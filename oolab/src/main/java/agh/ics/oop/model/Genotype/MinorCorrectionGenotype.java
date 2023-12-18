package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.List;

public class MinorCorrectionGenotype extends AbstractGenotype {

    public MinorCorrectionGenotype(){
        super();
    }
    @Override
    public List<MapDirection> mutate(Animal firstParent, Animal secondParent) {
        super.mutate(firstParent, secondParent);
        return null;
    }
}
