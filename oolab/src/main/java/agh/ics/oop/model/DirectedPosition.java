package agh.ics.oop.model;

public class DirectedPosition {
    private final Vector2d position;
    private final MapDirection direction;
    public DirectedPosition(Vector2d position, MapDirection direction){
        this.position = position;
        this.direction = direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }
}
