package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.List;

public class RandomGenotype extends AbstractGenotype{

    public RandomGenotype(){
        super();
    }
    @Override
    public List<MapDirection> mutate(Animal firstParent, Animal secondParent) {
        super.mutate(firstParent, secondParent);
        return childGenotype;
    }
}
