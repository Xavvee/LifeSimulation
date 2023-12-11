package agh.ics.oop.model;

import java.util.Objects;

public class Animal implements WorldElement {

    private final Vector2d lowerBound = new Vector2d(0,0);
    private final Vector2d upperBound = new Vector2d(4,4);
    private MapDirection direction;

    private Vector2d position;
    public Animal(){
        this(new Vector2d(2,2));
    }

    public Animal(Vector2d position){
        this.direction = MapDirection.NORTH;
        this.position = position;
    }

    @Override
    public String toString(){
        return switch (direction){
            case NORTH -> "^";
            case WEST -> "<";
            case SOUTH -> "v";
            case EAST -> ">";
        };
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void move(MoveDirection direction, MoveValidator validator){
        switch (direction) {
            case FORWARD -> {
                Vector2d newPosition = position.add(this.direction.toUnitVector());
                if(validator.canMoveTo(newPosition)){
                    this.position = newPosition;
                }
            }
            case BACKWARD -> {
                Vector2d newPosition = position.subtract(this.direction.toUnitVector());
                if(validator.canMoveTo(newPosition)){
                    this.position = newPosition;
                }
            }
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
        }
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public Vector2d getPosition() {
        return position;
    }

}
