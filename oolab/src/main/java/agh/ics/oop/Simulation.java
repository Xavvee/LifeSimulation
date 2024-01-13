package agh.ics.oop;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Map.InflowsAndOutflows;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.Map.WorldMap;

import java.util.*;

public class Simulation {


    private List<MoveDirection> moveDirections;
    private List<Animal> animals;
    private List<Grass> grasses;
    private final WorldMap map;
    private int daysCount;
    private final int energyPerGrass;
    private final int minimumNumberOfMutations;
    private final int maximumNumberOfMutations;
    private final int genomeLength;
    private final int sufficientEnergyForReproduction;
    private final int energyNeededForReproduction;


    public Simulation(WorldMap map, int energyPerGrass, int minimumNumberOfMutations, int maximumNumberOfMutations, int genomeLength, int sufficientEnergyForReproduction, int energyNeededForReproduction){
        this.map = map;
        this.animals = map.getListOfAnimals();
        this.grasses = map.getListOfGrasses();
        this.energyPerGrass = energyPerGrass;
        this.daysCount = 0;
        this.minimumNumberOfMutations = minimumNumberOfMutations;
        this.maximumNumberOfMutations = maximumNumberOfMutations;
        this.genomeLength = genomeLength;
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

        }
    }

    public void simulateOneDay(){
        // 1. Usunięcie martwych zwierząt
        deleteDeadAnimals();
        // 2. Skręt i przemieszczanie zwierząt

        // 3. Konsumpcja roślin, które weszły zwierzaki
        consume();
        // 4. Rozmnażanie się najedzonych zwierzaków na tym samym polu.
        reproduce();
        // 5. Wzrastanie nowych roślin na wybranych polach.
        spawnGrassAndAddToList();
        subtractEnergy();
    }


    public void subtractEnergy(){
        for(Animal animal : animals){
            animal.setEnergy(animal.getEnergy()-1);
        }
    }

    public void deleteDeadAnimals(){
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() == 0) {
                iterator.remove();
                map.removeElement(animal.getPosition());
            }
        }
    }

    public void reproduce(){

    }

    public void consume(){

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
