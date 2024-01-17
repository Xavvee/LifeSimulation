package agh.ics.oop.model.Elements;

import agh.ics.oop.model.Vector2d;

import java.util.Map;

public interface WorldElement {

    /**
     * Return position of an object
     *
     * Returns the position of wanted animal or grass.
     */
    Vector2d position();

    /**
     * Converts standard String representation into a wanted one.
     */
    String toString();


    String getColor();

    Map<String, String> getStat();

}
