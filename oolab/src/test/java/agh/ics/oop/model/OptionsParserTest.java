package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static agh.ics.oop.OptionsParser.parse;
import static agh.ics.oop.model.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionsParserTest {

    @Test
    public void testOptionsParser(){
        // given
        String[] directions = {"f", "b", "r", "r", "l", "l","l", "f"};
        // when
        List<MoveDirection> convDirections =  parse(directions);
        // then
        MoveDirection[] moveArray = {FORWARD, BACKWARD, RIGHT, RIGHT, LEFT, LEFT, LEFT, FORWARD};
        List<MoveDirection> result = Arrays.asList(moveArray);
        assertEquals(convDirections, result);
    }

    @Test
    public void testOptionsParserTwo() {
        // given
        String[] directions = {"f", "b", "r", "o", "p", "w", "r", "l", "l", "w", "l", "p", "f"};

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            // when
            List<MoveDirection> convDirections = parse(directions);
        });
    }
}
