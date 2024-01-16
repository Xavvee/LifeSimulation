package agh.ics.oop.model.Genotype;

public class RandomGenotypeFactory implements GenotypeFactory{
    @Override
    public Genotype makeGenotype(int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations) {
        return new RandomGenotype(genomeLength,minimumNumberOfMutations,maximumNumberOfMutations);
    }

    @Override
    public String getType() {
        return "Pełna losowość";
    }
}
