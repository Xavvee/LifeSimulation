package agh.ics.oop.model;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Genotype.GenotypeType;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.Globe;
import agh.ics.oop.model.Map.MapType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobeTest {



    // sprawdzenie czy wartości się dobrze przypisują
    @Test
    public void generalMapTest(){
        //given
        AbstractWorldMap abstractWorldMap = new Globe(15,25,30,8,4,30,2,4,10, GenotypeType.MINOR_CORRECTION, MapType.GLOBE);
        //when
        List<Animal> animals = abstractWorldMap.getListOfAnimals();
        List<Grass> grasses = abstractWorldMap.getListOfGrasses();
        int genomeLength = abstractWorldMap.getGenomeLength();
        int maximumNumberOfMutations = abstractWorldMap.getMaximumNumberOfMutations();
        int minimumNumberOfMutations = abstractWorldMap.getMinimumNumberOfMutations();
        int width = abstractWorldMap.getWidth();
        int height = abstractWorldMap.getHeight();
        MapType mapType = abstractWorldMap.getMapType();
        GenotypeType genotypeType = abstractWorldMap.getGenotypeType();
        //then
        System.out.println(abstractWorldMap);
        assertEquals(animals.size(), 8);
        assertEquals(grasses.size(), 30);
        assertEquals(genomeLength, 10);
        assertEquals(animals.get(0).getGenotype().getGenomeLength(), 10);
        assertEquals(maximumNumberOfMutations, 4);
        assertEquals(animals.get(0).getGenotype().getMaximumNumberOfMutations(), 4);
        assertEquals(minimumNumberOfMutations, 2);
        assertEquals(animals.get(0).getGenotype().getMinimumNumberOfMutations(), 2);
        assertEquals(width, 25);
        assertEquals(height, 15);
        assertEquals(mapType, MapType.GLOBE);
        assertEquals(genotypeType, GenotypeType.MINOR_CORRECTION);
    }
}
