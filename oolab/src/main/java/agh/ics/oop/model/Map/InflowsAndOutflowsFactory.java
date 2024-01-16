package agh.ics.oop.model.Map;

import agh.ics.oop.model.Genotype.GenotypeType;
import agh.ics.oop.presenter.StartConfigurations;

public class InflowsAndOutflowsFactory implements MapFactory{
    @Override
    public WorldMap makeMap(StartConfigurations startConfigurations) {
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
                GenotypeType.RANDOM);
    }

    @Override
    public String getType() {
        return "Przypływy i odpływy";
    }
}
