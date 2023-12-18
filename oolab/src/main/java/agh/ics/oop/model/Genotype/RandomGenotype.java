package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Animal;

import java.util.List;

public class RandomGenotype extends AbstractGenotype{

    public RandomGenotype(){
        super();
    }
    @Override
    public List<Integer> mutate(Animal firstParent, Animal secondParent) {
        super.mutate(firstParent, secondParent);
        return null;
    }
}
