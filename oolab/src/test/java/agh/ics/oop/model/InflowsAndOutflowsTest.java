package agh.ics.oop.model;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Genotype.MinorCorrectionGenotypeFactory;
import agh.ics.oop.model.Genotype.RandomGenotypeFactory;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.InflowsAndOutflows;
import agh.ics.oop.model.Map.MapType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InflowsAndOutflowsTest {


    @Test
    public void generateWatersTest(){
        //given
        InflowsAndOutflows map = new InflowsAndOutflows(10,20, 0, 0,
                0,50,1,1,
                8, new MinorCorrectionGenotypeFactory());
        //when
        Map<Vector2d, Water> waters = map.getWaters();
        //then
        assertEquals(32, waters.size());
        System.out.println(waters);
        System.out.println(map.getWaterLowerLeftCorner());
        System.out.println(map.getWaterUpperRightCorner());
    }

    @Test
    public void generalMapTest(){
        //given
        AbstractWorldMap abstractWorldMap = new InflowsAndOutflows(10,20,25,7,5,25,1,5,16, new RandomGenotypeFactory());
        // when
        List<Animal> animals = abstractWorldMap.getListOfAnimals();
        List<Grass> grasses = abstractWorldMap.getListOfGrasses();
        int genomeLength = abstractWorldMap.getGenomeLength();
        int maximumNumberOfMutations = abstractWorldMap.getMaximumNumberOfMutations();
        int minimumNumberOfMutations = abstractWorldMap.getMinimumNumberOfMutations();
        int width = abstractWorldMap.getWidth();
        int height = abstractWorldMap.getHeight();
        MapType mapType = abstractWorldMap.getMapType();
        int watersNumber = abstractWorldMap.getWaters().size();
        //then
        System.out.println(abstractWorldMap);
        assertEquals(animals.size(), 7);
        assertEquals(grasses.size(), 25);
        assertEquals(genomeLength, 16);
        assertEquals(animals.get(0).getGenotype().getGenomeLength(), 16);
        assertEquals(maximumNumberOfMutations, 5);
        assertEquals(animals.get(0).getGenotype().getMaximumNumberOfMutations(), 5);
        assertEquals(minimumNumberOfMutations, 1);
        assertEquals(animals.get(0).getGenotype().getMinimumNumberOfMutations(), 1);
        assertEquals(width, 20);
        assertEquals(height, 10);
        assertEquals(mapType, MapType.INFLOWS_AND_OUTFLOWS);
        assertEquals(watersNumber, ((int) width * 0.4 ) * ((int) height * 0.4) );
    }
}
