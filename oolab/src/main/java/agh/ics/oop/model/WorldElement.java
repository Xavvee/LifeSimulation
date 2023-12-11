package agh.ics.oop.model;

public interface WorldElement {

    /**
     * Return position of an object
     *
     * Returns the position of wanted animal or grass.
     */
    Vector2d getPosition();

    /**
     * Converts standard String representation into a wanted one.
     */
    String toString();




}
