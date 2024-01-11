package agh.ics.oop.model;

import agh.ics.oop.model.Genotype.AbstractGenotype;
import agh.ics.oop.model.Genotype.MinorCorrectionGenotype;
import agh.ics.oop.model.Genotype.RandomGenotype;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenotypeTest {

    @Test
    public void testGenotypeGeneration(){
        // given
        AbstractGenotype randomGenotype = new RandomGenotype();
        // when
        List<MapDirection> genotype = randomGenotype.generateGenotype();
        // then
        assertEquals(randomGenotype.NUMBER_OF_GENES, genotype.size());
        for (MapDirection direction : genotype) {
            assertTrue(direction.ordinal() < 8);
        }
        System.out.println(genotype);
    }

    @Test
    public void testMinorCorrectionGenotype(){
        // given
        Animal firstParent = new Animal();
        Animal secondParent = new Animal();
        firstParent.setEnergy(150);
        AbstractGenotype abstractGenotype = new MinorCorrectionGenotype();
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
        Animal firstParent = new Animal();
        Animal secondParent = new Animal();
        firstParent.setEnergy(150);
        AbstractGenotype abstractGenotype = new RandomGenotype();
        // when
        List<MapDirection> childGenotype = abstractGenotype.mutate(firstParent, secondParent);
        // then
        assertNotNull(childGenotype);
        System.out.println(firstParent.getGenotype().getGenotype());
        System.out.println(secondParent.getGenotype().getGenotype());
        System.out.println(childGenotype);
    }
}
