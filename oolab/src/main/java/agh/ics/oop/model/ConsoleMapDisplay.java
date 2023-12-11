package agh.ics.oop.model;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;

public class ConsoleMapDisplay implements MapChangeListener {
    private int totalUpdates;

    public ConsoleMapDisplay(){
        this.totalUpdates = 0;
    }
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        totalUpdates += 1;
        System.out.println("Total number of changes: " + totalUpdates + ". Operation: " + message);
        System.out.println("Map id: " + worldMap.getId());
        System.out.println(worldMap);
    }

}

