package agh.ics.oop.model.Map;

import agh.ics.oop.Comparator;
import agh.ics.oop.MapVisualizer;
import agh.ics.oop.PositionAlreadyOccupied;
import agh.ics.oop.model.*;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.WorldElement;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected int WIDTH = 20;
    protected int HEIGHT = 10;
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer visualizer = new MapVisualizer(this);

    protected Comparator XComparator = new Comparator(true);

    protected Comparator YComparator = new Comparator(false);
    protected TreeSet<Vector2d> sortedX = new TreeSet<>(XComparator);
    protected TreeSet<Vector2d> sortedY = new TreeSet<>(YComparator);
    protected List<MapChangeListener> observers;

    protected UUID id;
    public AbstractWorldMap(){
        this.observers = new ArrayList<>();
        this.addObserver(new ConsoleMapDisplay());
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(this.getUpperRight()) && (this.getLowerLeft().precedes(position));
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

    // rusza zwierzęciem po mapie, ogólnie działa git, chyba nie trzeba zmieniać
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
        return animals.get(position);
    }

    @Override
    public boolean generateGrass(){
        return true;
    }

    @Override
    public boolean spawnGrass(){
        return true;
    }

    @Override
    public Vector2d getEquatorBounds(){
        int middleOfY = (int) HEIGHT/2;
        int tenPercentOfHeight = (int) (HEIGHT*0.1);

        return new Vector2d(middleOfY - tenPercentOfHeight, middleOfY + tenPercentOfHeight);
    }

    @Override
    public String toString(){
        return visualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    @Override
    public Map<Vector2d, WorldElement> getElements() {
        return new HashMap<>(animals);
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
    public abstract Boundary getCurrentBounds();

    protected abstract Vector2d getUpperRight();
    protected abstract Vector2d getLowerLeft();

}
