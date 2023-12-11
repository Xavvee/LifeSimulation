package agh.ics.oop.model;

public interface MapChangeListener {
    /**
     * Notifies of changes made on the map.
     * @param worldMap
     * @param message
     */

    void mapChanged(WorldMap worldMap, String message);
}
