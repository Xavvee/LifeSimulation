package agh.ics.oop;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Map.InflowsAndOutflows;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.Map.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
            map.move( animals.get(index));
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



    public void simulateOneDay(){
        // 1. Usunięcie martwych zwierząt

        // 2. Skręt i przemieszczanie zwierząt

        // 3. Konsumpcja roślin, które weszły zwierzaki

        // 4. Rozmnażanie się najedzonych zwierzaków na tym samym polu.

        // 5. Wzrastanie nowych roślin na wybranych polach.

    }

    public void deleteDeadAnimals(){

    }
    public void reproduce(Animal firstAnimal, Animal secondAnimal){

    }

    public void consume(){

    }

    public void incrementDayCount(){
        this.daysCount++;
    }

    public void manageWaters(){
        if (map instanceof InflowsAndOutflows) {
            Map<Vector2d, Water> waters = ((InflowsAndOutflows) map).getWaters();
            Random random = new Random();

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
