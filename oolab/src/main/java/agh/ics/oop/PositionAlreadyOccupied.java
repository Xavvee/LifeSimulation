package agh.ics.oop;

import agh.ics.oop.model.Vector2d;

public class PositionAlreadyOccupied extends Exception{
    public PositionAlreadyOccupied(Vector2d position){
        super("Position " + position + " is already occupied.");
    }
}
