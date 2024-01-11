package agh.ics.oop.model.Elements;

import agh.ics.oop.model.Vector2d;

public record Water(Vector2d position) implements WorldElement {

    @Override
    public String toString() {
        return "#";
    }
}
