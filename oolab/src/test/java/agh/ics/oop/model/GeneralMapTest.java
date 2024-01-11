package agh.ics.oop.model;


import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.Globe;
import org.junit.jupiter.api.Test;



public class GeneralMapTest {
    @Test
    public void testGetEquatorBounds(){
        //given
        AbstractWorldMap abstractWorldMap = new Globe(10);
        //when
        Vector2d boundaries = abstractWorldMap.getEquatorBounds();
        //then
        System.out.println(boundaries);

    }
}
