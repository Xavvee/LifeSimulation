package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.Map.GrassField;
import agh.ics.oop.model.Map.WorldMap;
import javafx.application.Application;

import java.util.List;

public class World {
    public static void main(String[] args){
        List<Vector2d> positions = List.of(new Vector2d(0,0), new Vector2d(0,2), new Vector2d(3,6), new Vector2d(1,0));
        WorldMap map = new GrassField(4);
        Simulation simulation = new Simulation(positions, map);
        simulation.run();
        System.out.println("System zakończył działanie.");
    }


    static void run(MoveDirection[] directions){
        for (MoveDirection dir : directions) {
            switch (dir){
                case FORWARD -> System.out.println("Zwierzak idzie do przodu.");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu.");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo.");
                case LEFT -> System.out.println("Zwierzak skręca w lewo.");
                default -> {
                }
            }
        }
    }
}

