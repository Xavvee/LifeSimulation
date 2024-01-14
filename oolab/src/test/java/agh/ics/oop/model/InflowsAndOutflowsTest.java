package agh.ics.oop.model;

import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Genotype.GenotypeType;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.Globe;
import agh.ics.oop.model.Map.InflowsAndOutflows;
import agh.ics.oop.model.Map.MapType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InflowsAndOutflowsTest {


    @Test
    public void generateWatersTest(){
        //given
        AbstractWorldMap abstractWorldMap = new InflowsAndOutflows(10,20, 0, 0, 0,50,1,1,8, GenotypeType.MINOR_CORRECTION, MapType.GLOBE);
        //when
        Map<Vector2d, Water> waters = abstractWorldMap.getWaters();
        //then
        assertEquals(8, waters.size());
        System.out.println(waters);
        System.out.println(((InflowsAndOutflows) abstractWorldMap).getWaterLowerLeftCorner());
        System.out.println(((InflowsAndOutflows) abstractWorldMap).getWaterUpperRightCorner());
    }

    @Test
    public void generalMapTest(){
        //given
        AbstractWorldMap abstractWorldMap = new InflowsAndOutflows(15,25,30,8,4,30,2,4,10, GenotypeType.MINOR_CORRECTION, MapType.INFLOWS_AND_OUTFLOWS);
        //when
        //then
        System.out.println(abstractWorldMap);
        System.out.println(abstractWorldMap.getGrasses().size());
        System.out.println(abstractWorldMap.getEquatorBounds());
    }
}
