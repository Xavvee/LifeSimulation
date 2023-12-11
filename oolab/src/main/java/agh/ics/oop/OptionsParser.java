package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.LinkedList;
import java.util.List;

import static agh.ics.oop.model.MoveDirection.*;

public class OptionsParser {
    public static List<MoveDirection> parse(List<String> directions) throws IllegalArgumentException{
        List<MoveDirection> directionList = new LinkedList<>();
        for (String direction : directions) {
            switch (direction){
                case "f" -> directionList.add(FORWARD);
                case "b" -> directionList.add(BACKWARD);
                case "r" -> directionList.add(RIGHT);
                case "l" -> directionList.add(LEFT);
                default -> throw new IllegalArgumentException(direction + " is not legal move specification");
            }
        }

        return directionList;
    }
}

