package agh.ics.oop.model;


import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Elements.Grass;
import agh.ics.oop.model.Genotype.MinorCorrectionGenotypeFactory;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.Globe;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GeneralMapTest {
    @Test
    public void testGetEquatorBounds(){
        //given
        AbstractWorldMap abstractWorldMap1 = new Globe(18, 10, 0, 0,0,50,1,1,8, new MinorCorrectionGenotypeFactory());
        AbstractWorldMap abstractWorldMap2 = new Globe(19, 10, 0, 0,0,50,1,1,8, new MinorCorrectionGenotypeFactory());
        AbstractWorldMap abstractWorldMap3 = new Globe(20, 10, 0, 0,0,50,1,1,8, new MinorCorrectionGenotypeFactory());
        AbstractWorldMap abstractWorldMap4 = new Globe(21, 10, 0, 0,0,50,1,1,8, new MinorCorrectionGenotypeFactory());
        AbstractWorldMap abstractWorldMap5 = new Globe(22, 10, 0, 0,0,50,1,1,8, new MinorCorrectionGenotypeFactory());
        //when
        Vector2d boundaries1 = abstractWorldMap1.getEquatorBounds();
        Vector2d boundaries2 = abstractWorldMap2.getEquatorBounds();
        Vector2d boundaries3 = abstractWorldMap3.getEquatorBounds();
        Vector2d boundaries4 = abstractWorldMap4.getEquatorBounds();
        Vector2d boundaries5 = abstractWorldMap5.getEquatorBounds();
        //then
        System.out.println(boundaries1);
        System.out.println(boundaries2);
        System.out.println(boundaries3);
        System.out.println(boundaries4);
        System.out.println(boundaries5);
    }

    @Test
    public void testGeneratingAnimals(){
        //given
        AbstractWorldMap abstractWorldMap = new Globe(10,10,0,6,0,50,1,1,8, new MinorCorrectionGenotypeFactory());
        //when
        Map<Vector2d, Animal> animals = abstractWorldMap.getAnimals();
        //then
        assertEquals(6, animals.size());
        System.out.println(animals);
    }

    @Test
    public void testGeneratingGrass(){
        //given
        AbstractWorldMap abstractWorldMap1 = new Globe(10,10,6,0,0,50,1,1,8, new MinorCorrectionGenotypeFactory());
        AbstractWorldMap abstractWorldMap2 = new Globe(30,10,10,0,0,50,1,1,8, new MinorCorrectionGenotypeFactory());
        //when
        Map<Vector2d, Grass> grasses1 = abstractWorldMap1.getGrasses();
        Map<Vector2d, Grass> grasses2 = abstractWorldMap2.getGrasses();
        //then
        assertEquals(6, grasses1.size());
        assertEquals(10, grasses2.size());
        System.out.println(grasses1);
        System.out.println(abstractWorldMap1.getEquatorBounds());
        System.out.println(grasses2);
        System.out.println(abstractWorldMap2.getEquatorBounds());
    }

    @Test
    public void testGeneratingGrassesAndAnimals(){
        //given
        AbstractWorldMap abstractWorldMap = new Globe(10,10,6,6,0,50,1,1,8, new MinorCorrectionGenotypeFactory());
        //when
        Map<Vector2d, Animal> animals = abstractWorldMap.getAnimals();
        Map<Vector2d, Grass> grasses = abstractWorldMap.getGrasses();
        //then
        assertEquals(6, animals.size());
        assertEquals(6, grasses.size());
        System.out.println(animals);
        System.out.println(grasses);
    }

    @Test
    public void testSpawningGrasses(){
        //given
        AbstractWorldMap abstractWorldMap1 = new Globe(10,10,6,0,2,50,1,1,8, new MinorCorrectionGenotypeFactory());
        AbstractWorldMap abstractWorldMap2 = new Globe(30,10,10,0,5,50,1,1,8, new MinorCorrectionGenotypeFactory());
        //when
        abstractWorldMap1.spawnGrass();
        abstractWorldMap1.spawnGrass();
        abstractWorldMap1.spawnGrass();
        Map<Vector2d, Grass> grasses1 = abstractWorldMap1.getGrasses();

        abstractWorldMap2.spawnGrass();
        abstractWorldMap2.spawnGrass();
        abstractWorldMap2.spawnGrass();
        Map<Vector2d, Grass> grasses2 = abstractWorldMap2.getGrasses();

        //then
        assertEquals(12, grasses1.size());
        assertEquals(25, grasses2.size());
        System.out.println(grasses1);
        System.out.println(abstractWorldMap1.getEquatorBounds());
        System.out.println(grasses2);
        System.out.println(abstractWorldMap2.getEquatorBounds());
    }
}
