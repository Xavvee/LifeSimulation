package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Elements.Animal;

public class RandomGenotypeFactory implements GenotypeFactory{
    @Override
    public Genotype makeGenotype(int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations) {
        return new RandomGenotype(genomeLength,minimumNumberOfMutations,maximumNumberOfMutations);
    }

    @Override
    public Genotype makeGenotype(int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations, Animal firstParent, Animal secondParent) {
        return new RandomGenotype(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations, firstParent, secondParent);
    }

    @Override
    public String getType() {
        return "Pełna losowość";
    }
}
