package agh.ics.oop.model.Elements;

import agh.ics.oop.model.Vector2d;

import java.util.Objects;

public record Water(Vector2d position) implements WorldElement {

    @Override
    public String toString() {
        return "#";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Water water = (Water) o;
        return Objects.equals(position, water.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
