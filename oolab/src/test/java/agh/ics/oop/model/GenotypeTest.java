package agh.ics.oop.model;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.Genotype.*;
import agh.ics.oop.model.Map.MapType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenotypeTest {

    @Test
    public void testGenotypeGeneration(){
        // given
        AbstractGenotype randomGenotype = new RandomGenotype(8,1,1);
        // when
        List<MapDirection> genotype = randomGenotype.generateGenotype();
        // then
        assertEquals(randomGenotype.getGenomeLength(), genotype.size());
        for (MapDirection direction : genotype) {
            assertTrue(direction.ordinal() < 8);
        }
        System.out.println(genotype);
    }

    @Test
    public void testMinorCorrectionGenotype(){
        // given
        Animal firstParent = new Animal(new Vector2d(0,0), 50, 8, 1, 1, new MinorCorrectionGenotypeFactory(), MapType.GLOBE);
        Animal secondParent = new Animal(new Vector2d(0,0), 50, 8, 1, 1, new MinorCorrectionGenotypeFactory(), MapType.GLOBE);
        firstParent.setEnergy(150);
        AbstractGenotype abstractGenotype = new MinorCorrectionGenotype(8,1,1);
        // when
        List<MapDirection> childGenotype = abstractGenotype.mutate(firstParent, secondParent);
        // then
        assertNotNull(childGenotype);
        System.out.println(firstParent.getGenotype().getGenotype());
        System.out.println(secondParent.getGenotype().getGenotype());
        System.out.println(childGenotype);
    }

    @Test
    public void testRandomGenotype(){
        // given
        Animal firstParent = new Animal(new Vector2d(0,0), 50, 8, 1, 1, new RandomGenotypeFactory(), MapType.GLOBE);
        Animal secondParent = new Animal(new Vector2d(0,0), 50, 8, 1, 1, new RandomGenotypeFactory(), MapType.GLOBE);
        firstParent.setEnergy(150);
        AbstractGenotype abstractGenotype = new RandomGenotype(8,1,1);
        // when
        List<MapDirection> childGenotype = abstractGenotype.mutate(firstParent, secondParent);
        // then
        assertNotNull(childGenotype);
        System.out.println(firstParent.getGenotype().getGenotype());
        System.out.println(secondParent.getGenotype().getGenotype());
        System.out.println(childGenotype);
    }
}
