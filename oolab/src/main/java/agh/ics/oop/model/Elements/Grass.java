package agh.ics.oop.model.Elements;

import agh.ics.oop.model.Vector2d;

import java.util.Map;
import java.util.Objects;

public record Grass(Vector2d position) implements WorldElement {

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public String getColor() {
        return "#138009";
    }

    @Override
    public Map<String, String> getStat() {
        return Map.of("typ obiektu", "trawa");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grass grass = (Grass) o;
        return Objects.equals(position, grass.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
