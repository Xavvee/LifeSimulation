package agh.ics.oop;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Genotype.GenotypeFactory;
import agh.ics.oop.model.Map.InflowsAndOutflows;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.Map.WorldMap;

import java.util.*;
import java.util.Comparator;

public class Simulation {


    private List<Animal> animals;
    private List<Grass> grasses;
    private final WorldMap map;
    private int daysCount;
    private final int energyPerGrass;
    private final int sufficientEnergyForReproduction;
    private final int energyNeededForReproduction;
    private GenotypeFactory genotypeFactory;


    
    public Simulation(WorldMap map, int energyPerGrass, int sufficientEnergyForReproduction, int energyNeededForReproduction, GenotypeFactory genotypeFactory){
        this.map = map;
        this.genotypeFactory = genotypeFactory;
        this.animals = map.getListOfAnimals();
        this.grasses = map.getListOfGrasses();
        this.energyPerGrass = energyPerGrass;
        this.daysCount = 0;
        this.sufficientEnergyForReproduction = sufficientEnergyForReproduction;
        this.energyNeededForReproduction = energyNeededForReproduction;
    }


    public void moveAnimals(){
        for( Animal animal : animals){
            map.move(animal);
        }
    }

    public void simulateOneDay(){
        synchronized (map) {
            // 1. Usunięcie martwych zwierząt
            deleteDeadAnimals();
            // 2. Skręt i przemieszczanie zwierząt
            moveAnimals();
            // 3. Konsumpcja roślin, na które weszły zwierzaki
            consume();
            // 4. Rozmnażanie się najedzonych zwierzaków na tym samym polu.
            reproduce();
            // 5. Wzrastanie nowych roślin na wybranych polach.
            spawnGrassAndAddToList();
            // 6. Inne rzeczy, które zawsze zachodzą.
            subtractEnergyAddAge();
            incrementDayCount();
            map.signalObservers("Liczba dni symulacji: " + daysCount);
        }
    }

    public void simulateXDays(int daysCount){
        for( int i = 0; i < daysCount; i++){
            simulateOneDay();
            System.out.println("Number of animals: " + this.animals.size());
            System.out.println("Number of grasses: " + this.grasses.size());
        }
    }

    public void subtractEnergyAddAge(){
        for(Animal animal : animals){
            animal.setEnergy(animal.getEnergy()-1);
            animal.setAge(animal.getAge()+1);
        }
    }

    public void deleteDeadAnimals(){
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() == 0) {
                iterator.remove();
                map.removeElement(animal.getPosition());
                map.removeAnimal(animal.getPosition());
                if(!map.isGrassAt(animal.getPosition()) && !map.isWaterAt(animal.getPosition())){
                    map.addFreeHex(animal.position());
                }
            } else if (map instanceof InflowsAndOutflows) {
                Map<Vector2d, Water> waterMap = ((InflowsAndOutflows) map).getWaters();
                Set<Vector2d> waterHexes = waterMap.keySet();
                if (waterHexes.contains(animal.getPosition())) {
                    iterator.remove();
                    map.removeElement(animal.getPosition());
                    map.removeAnimal(animal.getPosition());
                    if(!map.isGrassAt(animal.getPosition()) && !map.isWaterAt(animal.getPosition())){
                        map.addFreeHex(animal.position());
                    }
                }
            }
        }
    }


    public void deleteEntitiesOnWater() {
        Map<Vector2d, Water> waterMap = ((InflowsAndOutflows) map).getWaters();
        Iterator<Animal> animalIterator = animals.iterator();
        while (animalIterator.hasNext()) {
            Animal animal = animalIterator.next();
            Vector2d position = animal.getPosition();
            if (waterMap.containsKey(position)) {
                animalIterator.remove();
                map.removeElement(position);
                map.removeAnimal(position);
            }
        }

        Iterator<Grass> grassIterator = grasses.iterator();
        while (grassIterator.hasNext()) {
            Grass grass = grassIterator.next();
            Vector2d position = grass.position();
            if (waterMap.containsKey(position)) {
                grassIterator.remove();
                map.removeElement(position);
                map.removeGrass(position);
            }
        }
    }

    public void reproduce(){
        List<Animal> filteredAnimals = new ArrayList<>();
        for( Animal animal : animals){
            if(animal.getEnergy() >= sufficientEnergyForReproduction){
                filteredAnimals.add(animal);
            }
        }
        Map<Vector2d, List<Animal>> animalsAtPosition = new HashMap<>();
        for (Animal animal : filteredAnimals) {
            Vector2d position = animal.getPosition();
            animalsAtPosition.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
        }
        for (List<Animal> animalsOnSamePosition : animalsAtPosition.values()) {
             if (animalsOnSamePosition.size() >= 2) {
                animalsOnSamePosition.sort(Comparator
                        .comparingInt(Animal::getEnergy).reversed()
                        .thenComparingInt(Animal::getAge).reversed()
                        .thenComparingInt(Animal::getChildrenCount).reversed()
                );
                Animal firstParent = animalsOnSamePosition.get(0);
                Animal secondParent = animalsOnSamePosition.get(1);
                Animal child = new Animal(firstParent.getPosition(), firstParent, secondParent, energyNeededForReproduction, map.getGenomeLength(), map.getMinimumNumberOfMutations(), map.getMaximumNumberOfMutations(), genotypeFactory, map.getMapType());
                firstParent.setEnergy(firstParent.getEnergy()-energyNeededForReproduction);
                secondParent.setEnergy(secondParent.getEnergy()-energyNeededForReproduction);
                firstParent.addChild();
                secondParent.addChild();
                animals.add(child);
                map.addElement(child.getPosition());
                map.addAnimal(child);
            }
        }
    }

    public void consume(){
        Map<Vector2d, List<Animal>> animalsOnGrass = new HashMap<>();
        List<Grass> grassForRemoval = new ArrayList<>();
        for (Animal animal : animals) {
            Vector2d position = animal.getPosition();
            animalsOnGrass.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
        }

        for (Grass grass : grasses) {
            List<Animal> animalsAtPosition = animalsOnGrass.getOrDefault(grass.position(), Collections.emptyList());

            if (animalsAtPosition.size() > 1) {
                Animal winner = resolveConflictForPlants(animalsAtPosition);
                winner.addEnergy(energyPerGrass);
                grassForRemoval.add(grass);
            } else if (!animalsAtPosition.isEmpty()) {
                Animal singleAnimal = animalsAtPosition.get(0);
                singleAnimal.addEnergy(energyPerGrass);
                grassForRemoval.add(grass);
            }
        }
        removeConsumedGrass(grassForRemoval);
    }

    private void removeConsumedGrass(List<Grass> grassesToBeRemoved) {
        for( Grass grass : grassesToBeRemoved){
            this.map.removeGrass(grass.position());
            this.map.removeElement(grass.position());
            this.grasses.remove(grass);
        }
    }

    private Animal resolveConflictForPlants(List<Animal> animalsAtPosition) {
        animalsAtPosition.sort(Comparator
                .comparingInt(Animal::getEnergy).reversed()
                .thenComparingInt(Animal::getAge).reversed()
                .thenComparingInt(Animal::getChildrenCount).reversed()
        );
        return animals.get(0);
    }

    public void incrementDayCount(){
        this.daysCount++;
    }


    public void spawnGrassAndAddToList() {
        List<Grass> newGrasses = map.spawnGrass();
        grasses.addAll(newGrasses);
        if(map instanceof InflowsAndOutflows){
            manageWaters();
        }
    }

    public void manageWaters(){
        Random random = new Random();
        // if 0 -> enlarge, if 1 shrink
        int shrinkOrEnlarge = random.nextInt(2);
        // 0 -> north, 1 -> east, 2 -> south, 3 -> west
        int drawnNumber = random.nextInt(4);
        Vector2d lowerLeftWaterCorner = ((InflowsAndOutflows) map).getWaterLowerLeftCorner();
        Vector2d upperRightWaterCorner = ((InflowsAndOutflows) map).getWaterUpperRightCorner();
        Vector2d upperLeftWaterCorner = new Vector2d(lowerLeftWaterCorner.getX(), upperRightWaterCorner.getY());
        Vector2d lowerRightWaterCorner = new Vector2d(upperRightWaterCorner.getX(), lowerLeftWaterCorner.getY());
        switch (drawnNumber){
            case 0:
                if(shrinkOrEnlarge == 0 && upperRightWaterCorner.getY() + 1 < map.getHeight()) {
                    for( int i = upperLeftWaterCorner.getX(); i < upperRightWaterCorner.getX() + 1; i++){
                        for( int j = upperLeftWaterCorner.getY() + 1; j < upperLeftWaterCorner.getY() + 2; j++){
                            Vector2d position = new Vector2d(i, j);
                            ((InflowsAndOutflows) map).addWater(position);
                        }
                    }
                    ((InflowsAndOutflows) map).setWaterUpperRightCorner(new Vector2d(upperRightWaterCorner.getX(), upperRightWaterCorner.getY() + 1));
                    deleteEntitiesOnWater();
                } else if (shrinkOrEnlarge == 1 && upperRightWaterCorner.getY() - 1 > 0) {
                    calculateWater(upperRightWaterCorner, upperLeftWaterCorner, upperLeftWaterCorner.getY());
                    ((InflowsAndOutflows) map).setWaterUpperRightCorner(new Vector2d(upperRightWaterCorner.getX(), upperRightWaterCorner.getY() - 1));
                }
                break;
            case 1:
                if(shrinkOrEnlarge == 0 && upperRightWaterCorner.getX() + 1 < map.getWidth()){
                    for( int i = lowerRightWaterCorner.getX() + 1; i < lowerRightWaterCorner.getX() + 2; i++){
                        for( int j = lowerRightWaterCorner.getY(); j < upperLeftWaterCorner.getY() + 1; j++){
                            Vector2d position = new Vector2d(i, j);
                            ((InflowsAndOutflows) map).addWater(position);
                        }
                    }
                    ((InflowsAndOutflows) map).setWaterUpperRightCorner(new Vector2d(upperRightWaterCorner.getX() + 1, upperRightWaterCorner.getY()));
                    deleteEntitiesOnWater();
                } else if (shrinkOrEnlarge == 1 && upperRightWaterCorner.getX() - 1 > 0) {
                    calculateWater(lowerRightWaterCorner, lowerRightWaterCorner, upperLeftWaterCorner.getY());
                    ((InflowsAndOutflows) map).setWaterUpperRightCorner(new Vector2d(upperRightWaterCorner.getX() - 1, upperRightWaterCorner.getY()));
                }
                break;
            case 2:
                if(shrinkOrEnlarge == 0 && lowerLeftWaterCorner.getY() - 1 > 0){
                    for( int i = lowerLeftWaterCorner.getX(); i < lowerRightWaterCorner.getX() + 1; i++){
                        for( int j = lowerLeftWaterCorner.getY() - 1; j < lowerLeftWaterCorner.getY(); j++){
                            Vector2d position = new Vector2d(i, j);
                            ((InflowsAndOutflows) map).addWater(position);
                        }
                    }
                    ((InflowsAndOutflows) map).setWaterLowerLeftCorner(new Vector2d(lowerLeftWaterCorner.getX(), lowerLeftWaterCorner.getY() - 1));
                    deleteEntitiesOnWater();
                } else if (shrinkOrEnlarge == 1 && lowerLeftWaterCorner.getY() + 1 < upperLeftWaterCorner.getY()){
                    for( int i = lowerLeftWaterCorner.getX(); i < lowerRightWaterCorner.getX() + 1; i++){
                        for( int j = lowerLeftWaterCorner.getY(); j < lowerLeftWaterCorner.getY() + 1; j++){
                            Vector2d position = new Vector2d(i, j);
                            ((InflowsAndOutflows) map).removeWater(position);
                        }
                    }
                    ((InflowsAndOutflows) map).setWaterLowerLeftCorner(new Vector2d(lowerLeftWaterCorner.getX(), lowerLeftWaterCorner.getY() + 1));
                }
                break;
            case 3:
                if(shrinkOrEnlarge == 0 && lowerLeftWaterCorner.getX() - 1 > 0){
                    for( int i = lowerLeftWaterCorner.getX() - 1; i < lowerLeftWaterCorner.getX(); i++){
                        for( int j = lowerLeftWaterCorner.getY(); j < upperLeftWaterCorner.getY() + 1; j++){
                            Vector2d position = new Vector2d(i, j);
                            ((InflowsAndOutflows) map).addWater(position);
                        }
                    }
                    ((InflowsAndOutflows) map).setWaterLowerLeftCorner(new Vector2d(lowerLeftWaterCorner.getX() - 1, lowerLeftWaterCorner.getY()));
                    deleteEntitiesOnWater();
                } else if (shrinkOrEnlarge == 1 && lowerLeftWaterCorner.getX() + 1 < lowerRightWaterCorner.getX()) {
                    calculateWater(lowerLeftWaterCorner, lowerLeftWaterCorner, upperLeftWaterCorner.getY());
                    ((InflowsAndOutflows) map).setWaterLowerLeftCorner(new Vector2d(lowerLeftWaterCorner.getX() + 1, lowerLeftWaterCorner.getY()));
                }

                break;
        }

    }

    private void calculateWater(Vector2d endingCorner, Vector2d startingCorner, int y) {
        for( int i = startingCorner.getX(); i < endingCorner.getX() + 1; i++){
            for(int j = startingCorner.getY(); j < y + 1; j++){
                Vector2d position = new Vector2d(i, j);
                ((InflowsAndOutflows) map).removeWater(position);
            }
        }
    }


    public List<Animal> getAnimals() {
        return this.animals;
    }


    public int getDaysCount() {
        return daysCount;
    }

    public WorldMap getMap() {
        return map;
    }
}
