package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static agh.ics.oop.model.MoveDirection.*;
import static agh.ics.oop.model.MoveDirection.FORWARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrassFieldTest {

    @Test
    public void testGrassFieldFiveGrasses(){
        //given
        MoveDirection[] moveArray = {FORWARD,RIGHT,LEFT,FORWARD,FORWARD,FORWARD,RIGHT,FORWARD,FORWARD,FORWARD,FORWARD,RIGHT,LEFT,FORWARD,FORWARD,LEFT,FORWARD};
        List<MoveDirection> directions = Arrays.asList(moveArray);
        List<Vector2d> positions = List.of(new Vector2d(4,1), new Vector2d(6,7));
        WorldMap map = new GrassField(5);
        Simulation simulation = new Simulation(positions, directions, map);
        //when
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        //then
        assertTrue(animals.get(0).isAt(new Vector2d(1,4)));
        assertTrue(animals.get(1).isAt(new Vector2d(10,6)));
        assertEquals(animals.get(0).getDirection(),MapDirection.WEST);
        assertEquals(animals.get(1).getDirection(),MapDirection.EAST);
    }

    @Test
    public void testGrassFieldTenGrasses(){
        //given
        MoveDirection[] moveArray = {FORWARD,FORWARD,LEFT,RIGHT,FORWARD,BACKWARD,FORWARD,FORWARD,BACKWARD,FORWARD,FORWARD,BACKWARD,LEFT,FORWARD,BACKWARD,FORWARD,FORWARD};
        List<MoveDirection> directions = Arrays.asList(moveArray);
        List<Vector2d> positions = List.of(new Vector2d(0,0), new Vector2d(1,-1), new Vector2d(7,3));
        WorldMap map = new GrassField(10);
        Simulation simulation = new Simulation(positions, directions, map);
        //when
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        //then
        assertTrue(animals.get(0).isAt(new Vector2d(1,2)));
        assertTrue(animals.get(1).isAt(new Vector2d(1,5)));
        assertTrue(animals.get(2).isAt(new Vector2d(11,3)));
        assertEquals(animals.get(0).getDirection(),MapDirection.NORTH);
        assertEquals(animals.get(1).getDirection(),MapDirection.NORTH);
        assertEquals(animals.get(2).getDirection(),MapDirection.WEST);
    }

    @Test
    public void testGrassFieldFifteenGrasses(){
        //given
        MoveDirection[] moveArray = {FORWARD,RIGHT,LEFT,FORWARD,FORWARD,FORWARD,RIGHT,FORWARD,FORWARD,FORWARD,FORWARD,RIGHT,FORWARD,FORWARD,FORWARD,LEFT,FORWARD};
        List<MoveDirection> directions = Arrays.asList(moveArray);
        List<Vector2d> positions = List.of(new Vector2d(0,0), new Vector2d(-9,-9), new Vector2d(5,5),new Vector2d(4,3), new Vector2d(15,15));
        WorldMap map = new GrassField(15);
        Simulation simulation = new Simulation(positions, directions, map);
        //when
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        //then
        assertTrue(animals.get(0).isAt(new Vector2d(0,3)));
        assertTrue(animals.get(1).isAt(new Vector2d(-10,-9)));
        assertTrue(animals.get(2).isAt(new Vector2d(3,5)));
        assertTrue(animals.get(3).isAt(new Vector2d(4,5)));
        assertTrue(animals.get(4).isAt(new Vector2d(15,18)));
        assertEquals(animals.get(0).getDirection(),MapDirection.WEST);
        assertEquals(animals.get(1).getDirection(),MapDirection.WEST);
        assertEquals(animals.get(2).getDirection(),MapDirection.WEST);
        assertEquals(animals.get(3).getDirection(),MapDirection.NORTH);
        assertEquals(animals.get(4).getDirection(),MapDirection.NORTH);
    }
}
