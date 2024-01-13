package agh.ics.oop.model;

import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Genotype.GenotypeType;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.InflowsAndOutflows;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InflowsAndOutflowsTest {


    @Test
    public void generateWatersTest(){
        //given
        AbstractWorldMap abstractWorldMap = new InflowsAndOutflows(10,20, 0, 0, 0,50,1,1,8, GenotypeType.MINOR_CORRECTION);
        //when
        Map<Vector2d, Water> waters = abstractWorldMap.getWaters();
        //then
        assertEquals(8, waters.size());
        System.out.println(waters);
    }
}
