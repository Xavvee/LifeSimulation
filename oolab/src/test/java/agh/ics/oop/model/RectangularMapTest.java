package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Map.RectangularMap;
import agh.ics.oop.model.Map.WorldMap;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static agh.ics.oop.model.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RectangularMapTest {
    @Test
    public void testRectangularMapTwoAnimals(){
        //given
        MoveDirection[] moveArray = {FORWARD,RIGHT,LEFT,FORWARD,FORWARD,FORWARD,RIGHT,FORWARD,FORWARD,FORWARD,FORWARD,RIGHT,LEFT,FORWARD,FORWARD,LEFT,FORWARD};
        List<MoveDirection> directions = Arrays.asList(moveArray);
        List<Vector2d> positions = List.of(new Vector2d(4,1), new Vector2d(1,2));
        WorldMap map = new RectangularMap(4,4);
        Simulation simulation = new Simulation(positions, directions, map);
        //when
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        //then
        assertTrue(animals.get(0).isAt(new Vector2d(1,4)));
        assertTrue(animals.get(1).isAt(new Vector2d(3,1)));
        assertEquals(animals.get(0).getDirection(),MapDirection.WEST);
        assertEquals(animals.get(1).getDirection(),MapDirection.EAST);
    }

    @Test
    public void testRectangularMapThreeAnimals(){
        //given
        MoveDirection[] moveArray = {RIGHT,RIGHT,BACKWARD,FORWARD,FORWARD,BACKWARD,FORWARD,LEFT,LEFT,LEFT,FORWARD,BACKWARD,FORWARD,FORWARD,LEFT,FORWARD,RIGHT};
        List<MoveDirection> directions = Arrays.asList(moveArray);
        List<Vector2d> positions = List.of(new Vector2d(0,0), new Vector2d(0,3), new Vector2d(2,7), new Vector2d(7,3));
        WorldMap map = new RectangularMap(7,2);
        Simulation simulation = new Simulation(positions, directions, map);
        //when
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        //then
        assertTrue(animals.get(0).isAt(new Vector2d(2,2)));
        assertTrue(animals.get(1).isAt(new Vector2d(1,5)));
        assertTrue(animals.get(2).isAt(new Vector2d(2,5)));
        assertEquals(animals.get(0).getDirection(),MapDirection.NORTH);
        assertEquals(animals.get(1).getDirection(),MapDirection.EAST);
        assertEquals(animals.get(2).getDirection(),MapDirection.SOUTH);
    }


    @Test
    public void testRectangularMapFiveAnimals(){
        //given
        MoveDirection[] moveArray = {FORWARD,RIGHT,LEFT,FORWARD,FORWARD, FORWARD,RIGHT,FORWARD,FORWARD,FORWARD, FORWARD,LEFT,FORWARD,LEFT,LEFT, LEFT,FORWARD,RIGHT,LEFT};
        List<MoveDirection> directions = Arrays.asList(moveArray);
        List<Vector2d> positions = List.of(new Vector2d(0,0), new Vector2d(0,3), new Vector2d(9,9), new Vector2d(5,5),new Vector2d(4,3), new Vector2d(1,1));
        WorldMap map = new RectangularMap(6,6);
        Simulation simulation = new Simulation(positions, directions, map);
        //when
        simulation.run();
        List<Animal> animals = simulation.getAnimals();
        //then
        assertTrue(animals.get(0).isAt(new Vector2d(0,2)));
        assertTrue(animals.get(1).isAt(new Vector2d(0,3)));
        assertTrue(animals.get(2).isAt(new Vector2d(3,5)));
        assertTrue(animals.get(3).isAt(new Vector2d(4,4)));
        assertTrue(animals.get(4).isAt(new Vector2d(1,3)));
        assertEquals(animals.get(0).getDirection(),MapDirection.WEST);
        assertEquals(animals.get(1).getDirection(),MapDirection.EAST);
        assertEquals(animals.get(2).getDirection(),MapDirection.NORTH);
        assertEquals(animals.get(3).getDirection(),MapDirection.SOUTH);
        assertEquals(animals.get(4).getDirection(),MapDirection.WEST);
    }

}
