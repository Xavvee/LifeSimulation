package agh.ics.oop.model.Map;

import agh.ics.oop.model.Genotype.GenotypeFactory;
import agh.ics.oop.presenter.StartConfigurations;

public class InflowsAndOutflowsFactory implements MapFactory{
    @Override
    public WorldMap makeMap(StartConfigurations startConfigurations, GenotypeFactory genotypeFactory) {
        return new InflowsAndOutflows(
                startConfigurations.getNumber("height"),
                startConfigurations.getNumber("width"),
                startConfigurations.getNumber("initialGrass"),
                startConfigurations.getNumber("initialAnimal"),
                startConfigurations.getNumber("dailyNumberOfGrasses"),
                startConfigurations.getNumber("animalEnergy"),
                startConfigurations.getNumber("minimumNumberOfMutations"),
                startConfigurations.getNumber("maximumNumberOfMutations"),
                startConfigurations.getNumber("genomeLength"),
                genotypeFactory
        );
    }

    @Override
    public String getType() {
        return "Przypływy i odpływy";
    }
}
