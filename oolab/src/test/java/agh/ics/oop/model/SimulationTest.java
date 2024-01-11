package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Map.RectangularMap;
import agh.ics.oop.model.Map.WorldMap;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static agh.ics.oop.OptionsParser.parse;
import static agh.ics.oop.model.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationTest {
    @Test
    public void testSimulationThreeAnimals(){
        //given
        String[] args = {"f","l","r","f","f","f","r","f","f","f","f","f","f","l","l","l","f","l"};
        MoveDirection[] moveArray = {FORWARD,LEFT,RIGHT,FORWARD,FORWARD,FORWARD,RIGHT,FORWARD,FORWARD,FORWARD,FORWARD,FORWARD,FORWARD,LEFT,LEFT,LEFT,FORWARD,LEFT};
        List<MoveDirection> result = Arrays.asList(moveArray);

        List<MoveDirection> directions = parse(Arrays.stream(args).toList());
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4), new Vector2d(0,0));
        WorldMap map = new RectangularMap(4,4);
        Simulation simulation = new Simulation(positions, directions,map);
        //when
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        List<MoveDirection> correctDirections = simulation.getMoveDirections();

        //then
        assertEquals(result, correctDirections);
        assertTrue(animals.get(0).isAt(new Vector2d(2,4)));
        assertTrue(animals.get(1).isAt(new Vector2d(3,3)));
        assertTrue(animals.get(2).isAt(new Vector2d(3,0)));
        assertEquals(animals.get(0).getDirection(),MapDirection.NORTH);
        assertEquals(animals.get(1).getDirection(),MapDirection.SOUTH);
        assertEquals(animals.get(2).getDirection(),MapDirection.WEST);
    }

    @Test
    public void testSimulationTwoAnimals(){
        //given
        MoveDirection[] moveArray = {FORWARD,RIGHT,LEFT,FORWARD,FORWARD,FORWARD,RIGHT,FORWARD,FORWARD,FORWARD,FORWARD,LEFT,FORWARD,LEFT,LEFT,LEFT,FORWARD};
        List<MoveDirection> directions = Arrays.asList(moveArray);
        List<Vector2d> positions = List.of(new Vector2d(4,1), new Vector2d(0,3));
        WorldMap map = new RectangularMap(4,4);
        Simulation simulation = new Simulation(positions, directions, map);
        //when
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        //then
        assertTrue(animals.get(0).isAt(new Vector2d(2,4)));
        assertTrue(animals.get(1).isAt(new Vector2d(4,3)));
        assertEquals(animals.get(0).getDirection(),MapDirection.WEST);
        assertEquals(animals.get(1).getDirection(),MapDirection.SOUTH);
    }
}
