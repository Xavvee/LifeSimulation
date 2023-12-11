package agh.ics.oop.model;

import agh.ics.oop.PositionAlreadyOccupied;
import org.junit.jupiter.api.Test;

import java.util.List;

import static agh.ics.oop.OptionsParser.parse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaceTest {

    @Test
    public void testPlaceCorrect() throws PositionAlreadyOccupied {
        //given
        RectangularMap map = new RectangularMap(4,4);
        //when
        map.place(new Animal(new Vector2d(0,0)));
        map.place(new Animal(new Vector2d(1,1)));
        //then
        assertTrue(map.isOccupied(new Vector2d(0,0)));
        assertTrue(map.isOccupied(new Vector2d(1,1)));
    }
    @Test
    public void testPlaceIncorrect(){
        //given
        RectangularMap map = new RectangularMap(4,4);
        // then
        assertThrows(PositionAlreadyOccupied.class, () -> {
            // when
            map.place(new Animal(new Vector2d(0,0)));
            map.place(new Animal(new Vector2d(0,0)));
        });
    }

}
