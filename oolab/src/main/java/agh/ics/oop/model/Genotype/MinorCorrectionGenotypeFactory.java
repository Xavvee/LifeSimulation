package agh.ics.oop.model.Genotype;

public class MinorCorrectionGenotypeFactory implements GenotypeFactory{
    @Override
    public Genotype makeGenotype(int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations) {
        return new MinorCorrectionGenotype(genomeLength,minimumNumberOfMutations,maximumNumberOfMutations);
    }

    @Override
    public String getType() {
        return "Lekka korekta";
    }
}
