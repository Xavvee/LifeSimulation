package agh.ics.oop.model.Map;

import agh.ics.oop.model.*;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.WorldElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GrassField extends AbstractWorldMap {

    private final int numberOfGrasses;

    private static final int HEIGHT = Integer.MAX_VALUE;
    private static final int WIDTH = Integer.MAX_VALUE;
    private Map<Vector2d, Grass> grasses;


    public GrassField(int numberOfGrasses){
        super();
        this.numberOfGrasses = numberOfGrasses;
        this.grasses = new HashMap<>();
        this.animals = new HashMap<>();
        Random rand = new Random();
        for ( int i = 0; i < numberOfGrasses; i++){
            while (true){
                if(generateGrass(rand)){
                    break;
                }
            }
        }
    }
    private boolean generateGrass(Random rand){
        Vector2d randomPosition = new Vector2d(rand.nextInt((int) Math.sqrt(numberOfGrasses*10)), rand.nextInt((int) Math.sqrt(numberOfGrasses*10)));
        if(this.isOccupied(randomPosition)){
            return false;
        }
        grasses.put(randomPosition, new Grass(randomPosition));
        addElement(randomPosition);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && (!isOccupied(position) || !(objectAt(position) instanceof Animal));
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        if(super.objectAt(position) != null){
            return super.objectAt(position);
        }
        return grasses.get(position);
    }

    public Vector2d getUpperRight(){
        return new Vector2d(WIDTH, HEIGHT);
    }

    public Vector2d getLowerLeft(){
        return new Vector2d(-WIDTH, -HEIGHT);
    }


    public Vector2d calculateLowerLeft(){
        return new Vector2d(sortedX.first().getX(), sortedY.first().getY());
    }

    public Vector2d calculateUpperRight(){
        return new Vector2d(sortedX.last().getX(), sortedY.last().getY());
    }

    @Override
    public Map<Vector2d, WorldElement> getElements() {
        Map<Vector2d, WorldElement> allElements = new HashMap<>();
        allElements.putAll(super.getElements());
        allElements.putAll(this.grasses);
        return allElements;
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(calculateLowerLeft(), calculateUpperRight());
    }
}
