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
        MapDirection southEast = MapDirection.SOUTHEAST;
        MapDirection southWest = MapDirection.SOUTHWEST;
        MapDirection northEast = MapDirection.NORTHEAST;
        MapDirection northWest = MapDirection.NORTHWEST;
        // when
        MapDirection east1 = east.next();
        MapDirection southEast1 = southEast.next();
        MapDirection west1 = west.next();
        MapDirection northWest1 = northWest.next();
        MapDirection north1 = north.next();
        MapDirection northEast1 = northEast.next();
        MapDirection south1 = south.next();
        MapDirection southWest1 = southWest.next();
        // then
        assertEquals(MapDirection.SOUTH, southEast1);
        assertEquals(MapDirection.NORTH, northWest1);
        assertEquals(MapDirection.EAST, northEast1);
        assertEquals(MapDirection.WEST, southWest1);


    }


    @Test
    public void testNextPrev(){
        for (MapDirection dir : MapDirection.values()) {
            assertEquals(dir.next().previous(), dir );
        }
    }
}
