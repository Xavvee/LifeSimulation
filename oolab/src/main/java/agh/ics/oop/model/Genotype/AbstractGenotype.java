package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractGenotype implements Genotype {
    public final int NUMBER_OF_GENES = 7;
    protected List<MapDirection> childGenotype;
    protected List<MapDirection> genotype;
    public AbstractGenotype(){
        this.genotype = generateGenotype();
    }

    @Override
    public List<MapDirection> mutate(Animal firstParent, Animal secondParent) {
        childGenotype = new ArrayList<>();
        return childGenotype;
    }

    @Override
    public List<MapDirection> generateGenotype() {
        List<MapDirection> genotype = new ArrayList<>();
        for( int i = 0; i < NUMBER_OF_GENES; i++){
            int randomNumber = (int)Math.floor(Math.random() * (8));
            genotype.add(MapDirection.values()[randomNumber]);
        }
        return genotype;
    }

    @Override
    public List<MapDirection> getGenotype() {
        return genotype;
    }

}
