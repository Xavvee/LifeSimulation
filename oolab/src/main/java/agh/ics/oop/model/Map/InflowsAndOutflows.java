package agh.ics.oop.model.Map;

import agh.ics.oop.PositionAlreadyOccupied;
import agh.ics.oop.model.ConsoleMapDisplay;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.Elements.WorldElement;

import java.util.*;

public class InflowsAndOutflows extends AbstractWorldMap{
    private Vector2d leftLowerWaterBound;

    private Vector2d rightUpperWaterBound;

    private Map<Vector2d, Water> waters;

    public InflowsAndOutflows(int height, int width, int numberOfGrasses, int numberOfAnimals, int dailyNumberOfGrasses) {
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrasses = numberOfGrasses;
        this.height = height;
        this.width = width;
        this.observers = new ArrayList<>();
        this.addObserver(new ConsoleMapDisplay());
        this.id = UUID.randomUUID();
        this.waters = new HashMap<>();
        generateWaters();
        this.grasses = new HashMap<>();
        generateGrasses();
        this.animals = new HashMap<>();
        generateAnimals();
        this.dailyNumberOfGrasses = dailyNumberOfGrasses;
    }


    // function that generates the water hexes at the beginning
    private boolean generateWaters(){
        int waterHeight = (int) (height * 0.2);
        int waterWidth = (int) (width * 0.2);

        int lowerLeftWaterBound;
        int upperRightWaterBound;
        Random random = new Random();

        while(true){

        }
    }


    // function that manipulates the water hexes through the simulation
    private void manageWater(){

    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(this.getUpperRight()) && (this.getLowerLeft().precedes(position)) && !(objectAt(position) instanceof Water) ;
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        return null;
    }

    @Override
    public Map<Vector2d, WorldElement> getElements() {
        return null;
    }

    @Override
    public UUID getId() {
        return null;
    }
}
