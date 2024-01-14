package agh.ics.oop.model.Map;

import agh.ics.oop.model.ConsoleMapDisplay;
import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Elements.WorldElement;
import agh.ics.oop.model.Genotype.GenotypeType;
import agh.ics.oop.model.Vector2d;

import java.util.*;

public class InflowsAndOutflows extends AbstractWorldMap{
    private int waterWidth;
    private int waterHeight;
    private Vector2d waterLowerLeftCorner;
    private Vector2d waterUpperRightCorner;

    public InflowsAndOutflows(int height, int width, int numberOfGrasses, int numberOfAnimals, int dailyNumberOfGrasses, int startingEnergy, int minimumNumberOfMutations, int maximumNumberOfMutations, int genomeLength, GenotypeType genotypeType, MapType mapType) {
        this.genotypeType = genotypeType;
        this.mapType = mapType;
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrasses = numberOfGrasses;
        this.startingEnergy = startingEnergy;
        this.minimumNumberOfMutations = minimumNumberOfMutations;
        this.maximumNumberOfMutations = maximumNumberOfMutations;
        this.genomeLength = genomeLength;
        this.height = height;
        this.width = width;
        this.observers = new ArrayList<>();
        this.freeHexes = new ArrayList<>();
        generateFreeHexes();
        this.freeHexesAboveEquator = new ArrayList<>();
        this.freeHexesBelowEquator = new ArrayList<>();
        this.freeHexesInEquator = new ArrayList<>();
        calculateFreeHexes();
        this.addObserver(new ConsoleMapDisplay());
        this.id = UUID.randomUUID();
        this.waterWidth = (int) (width * 0.4);
        this.waterHeight = (int) (height * 0.4);
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
        this.waterLowerLeftCorner = lowerLeftWaterBound;
        this.waterUpperRightCorner = new Vector2d(upperRightWaterBound.getX() -1, upperRightWaterBound.getY() - 1);
        for( int i = lowerLeftWaterBound.getX(); i < upperRightWaterBound.getX(); i++){
            for(int j = lowerLeftWaterBound.getY(); j < upperRightWaterBound.getY(); j++){
                Vector2d position = new Vector2d(i,j);
                waters.put(position, new Water(position));
                subtractFreeHex(position);
            }
        }
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(this.getUpperRight()) && (this.getLowerLeft().precedes(position)) && !(objectAt(position) instanceof Water) ;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if(super.objectAt(position) != null){
            return  super.objectAt(position);
        }
        return waters.get(position);
    }

    @Override
    public Map<Vector2d, Water> getWaters() {
        return waters;
    }

    public void setWaterLowerLeftCorner(Vector2d waterLowerLeftCorner) {
        this.waterLowerLeftCorner = waterLowerLeftCorner;
    }

    public void setWaterUpperRightCorner(Vector2d waterUpperRightCorner) {
        this.waterUpperRightCorner = waterUpperRightCorner;
    }

    public Vector2d getWaterLowerLeftCorner() {
        return waterLowerLeftCorner;
    }

    public Vector2d getWaterUpperRightCorner() {
        return waterUpperRightCorner;
    }

    public void addWater(Vector2d position){
        this.waters.put(position, new Water(position));
        this.removeElement(position);
        if(!isOccupied(position)){
            subtractFreeHex(position);
        }
    }

    public void removeWater(Vector2d position){
        this.waters.remove(position);
        this.addElement(position);
        addFreeHex(position);
    }

}
