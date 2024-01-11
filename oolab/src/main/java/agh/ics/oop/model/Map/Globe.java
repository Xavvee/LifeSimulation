package agh.ics.oop.model.Map;

import agh.ics.oop.PositionAlreadyOccupied;
import agh.ics.oop.model.*;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.WorldElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Globe extends AbstractWorldMap{
    private final int numberOfGrasses;

    private static final int HEIGHT = Integer.MAX_VALUE;
    private static final int WIDTH = Integer.MAX_VALUE;
    private Map<Vector2d, Grass> grasses;

    public Globe(int numberOfGrasses){
        super();
        this.numberOfGrasses = numberOfGrasses;
        this.grasses = new HashMap<>();
        this.animals = new HashMap<>();
        Random rand = new Random();
        for ( int i = 0; i < numberOfGrasses; i++){
            while (true){
                if(generateGrass()){
                    break;
                }
            }
        }
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean place(Animal animal) throws PositionAlreadyOccupied {
        return false;
    }

    @Override
    public void move(Animal animal) {

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
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
    public Boundary getCurrentBounds() {
        return null;
    }

    @Override
    protected Vector2d getUpperRight() {
        return null;
    }

    @Override
    protected Vector2d getLowerLeft() {
        return null;
    }

    @Override
    public UUID getId() {
        return null;
    }
}
