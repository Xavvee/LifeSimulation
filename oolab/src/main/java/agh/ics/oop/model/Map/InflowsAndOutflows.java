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
    private void generateWaters(){
        int waterHeight = (int) (height * 0.2);
        int waterWidth = (int) (width * 0.2);

        Vector2d lowerLeftWaterBound;
        Vector2d upperRightWaterBound;
        Random random = new Random();
        Vector2d transition = new Vector2d(waterWidth, waterHeight);

        do {
            int xValue = random.nextInt(width);
            int yValue = random.nextInt(height);
            lowerLeftWaterBound = new Vector2d(xValue, yValue);
        } while (!lowerLeftWaterBound.add(transition).precedes(this.getUpperRight()));
        upperRightWaterBound = lowerLeftWaterBound.add(transition);

        for( int i = lowerLeftWaterBound.getX(); i < upperRightWaterBound.getX(); i++){
            for(int j = lowerLeftWaterBound.getY(); j < upperRightWaterBound.getY(); j++){
                Vector2d position = new Vector2d(i,j);
                waters.put(position, new Water(position));
                subtractFreeHex();
            }
        }
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

    @Override
    public Map<Vector2d, Water> getWaters() {
        return waters;
    }

}
