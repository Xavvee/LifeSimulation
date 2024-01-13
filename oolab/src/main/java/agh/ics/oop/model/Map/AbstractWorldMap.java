package agh.ics.oop.model.Map;

import agh.ics.oop.Comparator;
import agh.ics.oop.MapVisualizer;
import agh.ics.oop.PositionAlreadyOccupied;
import agh.ics.oop.model.*;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.WorldElement;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected int width;
    protected int height;
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);

    protected Comparator XComparator = new Comparator(true);

    protected Comparator YComparator = new Comparator(false);
    protected TreeSet<Vector2d> sortedX = new TreeSet<>(XComparator);
    protected TreeSet<Vector2d> sortedY = new TreeSet<>(YComparator);
    protected List<MapChangeListener> observers;
    protected Map<Vector2d, Grass> grasses;
    protected int numberOfGrasses;

    protected int numberOfAnimals;
    protected int dailyNumberOfGrasses;
    protected UUID id;
    public AbstractWorldMap(int height, int width, int numberOfGrasses, int numberOfAnimals, int dailyNumberOfGrasses){
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrasses = numberOfGrasses;
        this.height = height;
        this.width = width;
        this.observers = new ArrayList<>();
        this.addObserver(new ConsoleMapDisplay());
        this.id = UUID.randomUUID();
        this.grasses = new HashMap<>();
        generateGrasses();
        this.animals = new HashMap<>();
        generateAnimals();
        this.dailyNumberOfGrasses = dailyNumberOfGrasses;
    }

    // for test purposes
    public AbstractWorldMap(int height, int width, int numberOfGrasses){
        this.numberOfGrasses = numberOfGrasses;
        this.numberOfAnimals = 4;
        this.height = height;
        this.width = width;
        this.grasses = new HashMap<>();
        generateGrasses();
        this.animals = new HashMap<>();
    }

    protected AbstractWorldMap() {
    }

    protected void generateAnimals(){
        for ( int i = 0; i < numberOfAnimals; i++){
            while (true){
                if(generateAnimal()){
                    break;
                }
            }
        }
    }

    protected void generateGrasses(){
        for ( int i = 0; i < numberOfGrasses; i++){
            while (true){
                if(generateGrass()){
                    break;
                }
            }
        }
    }

    @Override
    public void spawnGrass(){
        for(int i = 0; i < dailyNumberOfGrasses; i++){
            while (true){
                if(generateGrass()){
                    break;
                }
            }
        }
    }

    @Override
    public boolean generateAnimal(){
        Random rand = new Random();
        Vector2d randomPosition = new Vector2d(rand.nextInt(width), rand.nextInt(height));
        if(this.isOccupied(randomPosition)){
            return false;
        }
        animals.put(randomPosition, new Animal(randomPosition));
        addElement(randomPosition);
        return true;
    }

    @Override
    public boolean generateGrass(){
        Random rand = new Random();
        Vector2d randomPosition;
        Vector2d equatorBounds = getEquatorBounds();
        if (rand.nextDouble() < 0.8) {
            randomPosition = new Vector2d(
                    rand.nextInt(width), rand.nextInt(equatorBounds.getX(), equatorBounds.getY()+1));
        } else {
            if(rand.nextDouble() < 0.5){
                randomPosition = new Vector2d(
                        rand.nextInt(width), rand.nextInt(equatorBounds.getX()));
            } else {
                randomPosition = new Vector2d(
                        rand.nextInt(width), rand.nextInt(equatorBounds.getY(), height - 1));
            }
        }
        if (isOccupied(randomPosition)) {
            return false;
        }
        grasses.put(randomPosition, new Grass(randomPosition));
        addElement(randomPosition);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getY() < this.height && position.getY() > 0;
    }


    @Override
    public boolean place(Animal animal) throws PositionAlreadyOccupied {
        if(canMoveTo(animal.position())){
            animals.put(animal.position(), animal);
            addElement(animal.position());
            mapChanged(animal + " placed at: " + animal.position());
            return true;
        } else {
            throw new PositionAlreadyOccupied(animal.position());
        }
    }

    @Override
    public void move(Animal animal) {
        if(animals.containsKey(animal.position())){
            Vector2d oldPosition = animal.position();
            animal.move(this);
            Vector2d newPosition = animal.position();
            if (canMoveTo(newPosition)) {
                animals.remove(oldPosition);
                removeElement(oldPosition);
                animals.put(newPosition, animal);
                addElement(newPosition);
            }
            mapChanged(animal + " " + oldPosition + " -> " + newPosition);
        }
    }

    protected void addElement(Vector2d element){
        sortedX.add(element);
        sortedY.add(element);
    }


    protected void removeElement(Vector2d element){
        sortedY.remove(element);
        sortedX.remove(element);
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement animalElement = animals.get(position);
        if (animalElement != null) {
            return animalElement;
        }
        return grasses.get(position);
    }


    @Override
    public Vector2d getEquatorBounds(){
        int middleOfY = height/2;
        double tenPercent = height * 0.1;
        int tenPercentOfHeight;
        if (tenPercent - Math.floor(tenPercent) >= 0.5) {
            tenPercentOfHeight = (int) Math.ceil(tenPercent);
        } else {
            tenPercentOfHeight = (int) Math.floor(tenPercent);
        }

        return new Vector2d(middleOfY - tenPercentOfHeight, middleOfY + tenPercentOfHeight - 1);
    }

    @Override
    public String toString(){
        return visualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    @Override
    public Map<Vector2d, WorldElement> getElements() {
        Map<Vector2d, WorldElement> allElements = new HashMap<>();
        allElements.putAll(this.animals);
        allElements.putAll(this.grasses);
        return allElements;
    }

    @Override
    public UUID getId(){
        return this.id;
    }

    public void addObserver(MapChangeListener listener){
        this.observers.add(listener);
    }

    public void removeObserver(MapChangeListener listener){
        this.observers.remove(listener);
    }


    protected void mapChanged(String message){
        for( MapChangeListener observer : observers){
            observer.mapChanged(this, message);
        }
    }
    public Boundary getCurrentBounds(){
        return new Boundary(getLowerLeft(), getUpperRight());
    };

    protected  Vector2d getUpperRight(){
        return new Vector2d(width, height);
    };
    protected Vector2d getLowerLeft(){
        return new Vector2d(0, 0);
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }

    public Map<Vector2d, Grass> getGrasses() {
        return grasses;
    }

}
