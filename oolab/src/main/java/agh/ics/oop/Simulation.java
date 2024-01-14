package agh.ics.oop;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Map.InflowsAndOutflows;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.Map.WorldMap;

import java.util.*;
import java.util.Comparator;

public class Simulation {


    private List<MoveDirection> moveDirections;
    private List<Animal> animals;
    private List<Grass> grasses;
    private final WorldMap map;
    private int daysCount;
    private final int energyPerGrass;
    private final int sufficientEnergyForReproduction;
    private final int energyNeededForReproduction;


    
    public Simulation(WorldMap map, int energyPerGrass, int sufficientEnergyForReproduction, int energyNeededForReproduction){
        this.map = map;
        this.animals = map.getListOfAnimals();
        this.grasses = map.getListOfGrasses();
        this.energyPerGrass = energyPerGrass;
        this.daysCount = 0;
        this.sufficientEnergyForReproduction = sufficientEnergyForReproduction;
        this.energyNeededForReproduction = energyNeededForReproduction;
    }


    /// do zmiany, powinno przeiterować się po zwierzętach i ruszyć każdym po kolei
    public void run(){
        int numberOfAnimals = animals.size();
        int iter = 0;
        for( MoveDirection direction : moveDirections){
            int index = iter%numberOfAnimals;
            map.move(animals.get(index));
            iter++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(Animal animal : animals){
            map.move(animal);
        }
    }


    public void moveAnimals(){
        for( Animal animal : animals){
            map.move(animal);
        }
    }

    public void simulateOneDay(){
        // 1. Usunięcie martwych zwierząt
        deleteDeadAnimals();
        // 2. Skręt i przemieszczanie zwierząt
        moveAnimals();
        // 3. Konsumpcja roślin, które weszły zwierzaki
        consume();
        // 4. Rozmnażanie się najedzonych zwierzaków na tym samym polu.
        reproduce();
        // 5. Wzrastanie nowych roślin na wybranych polach.
        spawnGrassAndAddToList();
        // 6. Inne rzeczy, które zawsze zachodzą.
        subtractEnergyAddAge();
        incrementDayCount();
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
            } else if (map instanceof InflowsAndOutflows) {
                Map<Vector2d, Water> waterMap = ((InflowsAndOutflows) map).getWaters();
                Set<Vector2d> waterHexes = waterMap.keySet();
                if (waterHexes.contains(animal.getPosition())) {
                    iterator.remove();
                    map.removeElement(animal.getPosition());
                }
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
                Animal child = new Animal(firstParent.getPosition(), firstParent, secondParent, energyNeededForReproduction, map.getGenomeLength(), map.getMinimumNumberOfMutations(), map.getMaximumNumberOfMutations(), map.getGenotypeType(), map.getMapType());
                firstParent.setEnergy(firstParent.getEnergy()-energyNeededForReproduction);
                secondParent.setEnergy(secondParent.getEnergy()-energyNeededForReproduction);
                firstParent.addChild();
                secondParent.addChild();
                animals.add(child);
                map.addElement(child.getPosition());
            }
        }
    }

    public void consume(){
        Map<Vector2d, List<Animal>> animalsOnGrass = new HashMap<>();

        for (Animal animal : animals) {
            Vector2d position = animal.getPosition();
            animalsOnGrass.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
        }

        for (Grass grass : grasses) {
            List<Animal> animalsAtPosition = animalsOnGrass.getOrDefault(grass.position(), Collections.emptyList());

            if (animalsAtPosition.size() > 1) {
                Animal winner = resolveConflictForPlants(animalsAtPosition);
                winner.addEnergy(energyPerGrass);
            } else if (!animalsAtPosition.isEmpty()) {
                Animal singleAnimal = animalsAtPosition.get(0);
                singleAnimal.addEnergy(energyPerGrass);
            }
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
        Map<Vector2d, Water> waters = ((InflowsAndOutflows) map).getWaters();
        Random random = new Random();
        // if 0 -> enlarge, if 1 shrink
        int shrinkOrEnlarge = random.nextInt(2);
        // 0 -> north, 1 -> east, 2 -> south, 3 -> west
        int drawnNumber = random.nextInt(4);
        Vector2d lowerLeftWaterCorner = ((InflowsAndOutflows) map).getWaterLowerLeftCorner();
        Vector2d upperRightWaterCorner = ((InflowsAndOutflows) map).getWaterUpperRightCorner();
        if(shrinkOrEnlarge == 0 ){
            switch (drawnNumber){
                case 0:
                    
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        } else{
            switch (drawnNumber){
                case 0:

                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }

    }

    public List<Animal> getAnimals() {
        return this.animals;
    }

    public List<MoveDirection> getMoveDirections() {
        return this.moveDirections;
    }

    public int getDaysCount() {
        return daysCount;
    }

}
