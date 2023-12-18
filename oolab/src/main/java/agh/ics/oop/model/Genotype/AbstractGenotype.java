package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenotype implements Genotype {
    public final int NUMBER_OF_GENES = 7;
    protected int numberOfGenes;

    protected List<Integer> genotype;
    public AbstractGenotype(){
        this.genotype = generateGenotype();
    }


    @Override
    public List<Integer> mutate(Animal firstParent, Animal secondParent) {
        List<Integer> childGenotype = new ArrayList<>();
        return childGenotype;
    }

    @Override
    public List<Integer> generateGenotype() {
        List<Integer> genotype = new ArrayList<>();
        for( int i = 0; i < NUMBER_OF_GENES; i++){
            genotype.add((int)Math.floor(Math.random() * (8)));
        }
        return genotype;
    }

    @Override
    public List<Integer> getGenotype() {
        return genotype;
    }



}
