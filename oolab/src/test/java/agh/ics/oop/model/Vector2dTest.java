package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    public void testEquals(){
        // given
        Vector2d vector1 = new Vector2d(2,2);
        Vector2d vector2 = new Vector2d(2,2);
        Vector2d vector3 = new Vector2d(1,8);
        // when
        boolean result1 = vector1.equals(vector2);
        boolean result2 = vector2.equals(vector3);
        // then
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    public void testToString(){
        // given
        Vector2d vector = new Vector2d(2,2);
        // when
        String result = vector.toString();
        // then
        assertEquals("(2,2)", result);
    }

    @Test
    public void testPrecedes(){
        // given
        Vector2d vector1 = new Vector2d(1,2);
        Vector2d vector2 = new Vector2d(1,3);
        Vector2d vector3 = new Vector2d(2,1);
        // when
        boolean result1 = vector1.precedes(vector2);
        boolean result2 = vector3.precedes(vector2);
        // then
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    public void testFollows(){
        // given
        Vector2d vector1 = new Vector2d(2,3);
        Vector2d vector2 = new Vector2d(1,3);
        Vector2d vector3 = new Vector2d(0,4);
        // when
        boolean result1 = vector1.follows(vector2);
        boolean result2 = vector3.follows(vector2);
        // then
        assertTrue(result1);
        assertFalse(result2);

    }

    @Test
    public void testUpperRight(){
        // given
        Vector2d vector1 = new Vector2d(2,2);
        Vector2d vector2 = new Vector2d(1,3);
        // when
        Vector2d vector3 = vector1.upperRight(vector2);
        // then
        assertEquals(new Vector2d(2,3),vector3);
    }

    @Test
    public void testLowerLeft(){
        // given
        Vector2d vector1 = new Vector2d(2,2);
        Vector2d vector2 = new Vector2d(1,3);
        // when
        Vector2d vector3 = vector1.lowerLeft(vector2);
        // then
        assertEquals(new Vector2d(1,2),vector3);
    }

    @Test
    public void testAdd(){
        // given
        Vector2d vector1 = new Vector2d(2,2);
        Vector2d vector2 = new Vector2d(1,3);
        // when
        Vector2d vector3 = vector1.add(vector2);
        // then
        assertEquals(new Vector2d(3,5),vector3);
    }

    @Test
    public void testSubtract(){
        // given
        Vector2d vector1 = new Vector2d(2,2);
        Vector2d vector2 = new Vector2d(1,3);
        // when
        Vector2d vector3 = vector1.subtract(vector2);
        // then
        assertEquals(new Vector2d(1,-1),vector3);
    }

    @Test
    public void testOpposite(){
        // given
        Vector2d vector1 = new Vector2d(2,2);
        // when
        Vector2d vector2 = vector1.opposite();
        // then
        assertEquals(new Vector2d(-2,-2),vector2);
    }

}
