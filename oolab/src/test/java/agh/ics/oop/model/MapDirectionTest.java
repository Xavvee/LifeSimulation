package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

    @Test
    public void testNext(){
        // given
        MapDirection east = MapDirection.EAST;
        MapDirection west = MapDirection.WEST;
        MapDirection north = MapDirection.NORTH;
        MapDirection south = MapDirection.SOUTH;
        // when
        MapDirection east1 = east.next();
        MapDirection west1 = west.next();
        MapDirection north1 = north.next();
        MapDirection south1 = south.next();
        // then
        assertEquals(MapDirection.SOUTH, east1);
        assertEquals(MapDirection.NORTH, west1);
        assertEquals(MapDirection.EAST, north1);
        assertEquals(MapDirection.WEST, south1);

    }

    @Test
    public void testPrevious(){
        // given
        MapDirection east = MapDirection.EAST;
        MapDirection west = MapDirection.WEST;
        MapDirection north = MapDirection.NORTH;
        MapDirection south = MapDirection.SOUTH;
        // when
        MapDirection east1 = east.previous();
        MapDirection west1 = west.previous();
        MapDirection north1 = north.previous();
        MapDirection south1 = south.previous();
        // then
        assertEquals(MapDirection.NORTH, east1);
        assertEquals(MapDirection.SOUTH, west1);
        assertEquals(MapDirection.WEST, north1);
        assertEquals(MapDirection.EAST, south1);
    }
}
