package agh.ics.oop.model.Genotype;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface GenotypeFactory {
    Genotype makeGenotype(int genomeLength,int minimumNumberOfMutations,int maximumNumberOfMutations);
    String getType();
    static List<GenotypeFactory> listFactoryGenotype =
            List.of(new MinorCorrectionGenotypeFactory(), new RandomGenotypeFactory());
    static Map<String,GenotypeFactory> factoryByNameGenotype =
            listFactoryGenotype.stream().collect(Collectors.toMap(
                    GenotypeFactory::getType,
                    genotypeFactory -> genotypeFactory
            ));

}
