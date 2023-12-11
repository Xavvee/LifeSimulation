package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.List;

import static agh.ics.oop.OptionsParser.parse;

public class World {
    public static void main(String[] args){
        List<MoveDirection> directions = parse(List.of(args));
        List<Vector2d> positions1 = List.of(new Vector2d(0,0), new Vector2d(0,2), new Vector2d(3,6), new Vector2d(1,0));
        List<Vector2d> positions2 = List.of(new Vector2d(4,5), new Vector2d(1,5), new Vector2d(2,2), new Vector2d(5,0));
        WorldMap map1 = new GrassField(4);
        WorldMap map2 = new RectangularMap(10,10);

        List<Simulation> simulations = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            List<Vector2d> positions = List.of(new Vector2d(0,0), new Vector2d(0,2), new Vector2d(3,6), new Vector2d(1,0));
            WorldMap map = new GrassField(4);
            simulations.add(new Simulation(positions, directions, map));
        }
        List<Simulation> simulations1 = new ArrayList<>();
        simulations1.add(new Simulation(positions1, directions, map1));
        simulations1.add(new Simulation(positions2, directions, map2));
        SimulationEngine engine = new SimulationEngine(simulations);
//        engine.runSync();
        engine.runAsyncInThreadPool();
        try{
            engine.awaitSimulationsEnd();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
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

