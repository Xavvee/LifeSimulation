package agh.ics.oop.model;

import agh.ics.oop.model.Genotype.GenotypeType;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.Globe;
import agh.ics.oop.model.Map.MapType;
import org.junit.jupiter.api.Test;

public class GlobeTest {


    @Test
    public void movementTest(){
        //given
        //when

        //then
    }
    @Test
    public void generalTest(){
        //given
        AbstractWorldMap abstractWorldMap = new Globe(15,25,30,8,4,30,2,4,10, GenotypeType.MINOR_CORRECTION, MapType.GLOBE);
        //when
        //then
        System.out.println(abstractWorldMap);
        System.out.println(abstractWorldMap.getGrasses().size());

    }
}
