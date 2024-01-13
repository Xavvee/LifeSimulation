package agh.ics.oop;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.Map.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {


    private List<MoveDirection> moveDirections;
    private final List<Animal> animals;
    private final WorldMap map;
    private int daysCount;

    public Simulation(List<Vector2d> startingPositions, List<MoveDirection> moveDirections, WorldMap map){
        this(startingPositions, map);
        this.moveDirections = moveDirections;
    }
    public Simulation(List<Vector2d> startingPositions, WorldMap map){
        this.map = map;
        List<Animal> animals = new ArrayList<>();
        for(Vector2d position : startingPositions){
            Animal animal = new Animal(position);
            try {
                if(map.place(animal)) {
                    animals.add(animal);
                }
            } catch (PositionAlreadyOccupied e) {
                System.out.println("Position at " + position.toString() + " is already occupied. Animal placement skipped.");
            }
        }
        this.animals = animals;
        this.daysCount = 0;
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
