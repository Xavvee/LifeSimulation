package agh.ics.oop.model.Map;

import agh.ics.oop.PositionAlreadyOccupied;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.Elements.WorldElement;

import java.util.Map;
import java.util.UUID;

public class InflowsAndOutflows extends AbstractWorldMap{
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
