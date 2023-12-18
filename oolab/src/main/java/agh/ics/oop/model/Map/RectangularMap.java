package agh.ics.oop.model.Map;


import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Vector2d;

import java.util.HashMap;


public class RectangularMap extends AbstractWorldMap {
    private final int height;
    private final int width;

    public RectangularMap(int height, int width){
        super();
        this.height = height;
        this.width = width;
        this.animals = new HashMap<>();
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && !isOccupied(position);
    }

    @Override
    protected void addElement(Vector2d element) {

    }

    @Override
    protected void removeElement(Vector2d element) {

    }

    public Vector2d getLowerLeft(){
        return new Vector2d(0,0);
    }

    public Vector2d getUpperRight(){
        return new Vector2d(width, height);
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(getLowerLeft(), getUpperRight());
    }
}
