package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractGenotype implements Genotype {
    public final int NUMBER_OF_GENES = 8;
    public final int MINIMAL_NUMBER_OF_MUTATIONS = 1;
    public final int MAXIMUM_NUMBER_OF_MUTATIONS = 1;
    protected List<MapDirection> childGenotype;
    protected List<MapDirection> genotype;
    public AbstractGenotype(){
        this.genotype = generateGenotype();
    }

    @Override
    public List<MapDirection> mutate(Animal firstParent, Animal secondParent) {
        childGenotype = new ArrayList<>();
        Random random = new Random();
        // if sideOfStrongerParent == 0 then it's left, else it's right
        int sideOfStrongerParent = random.nextInt(2);
        float firstParentPercentage = (float) firstParent.getEnergy() /(secondParent.getEnergy() + firstParent.getEnergy());
        float secondParentPercentage = (float) secondParent.getEnergy() /(secondParent.getEnergy() + firstParent.getEnergy());
        int numberOfFirstParentGenes = Math.round(NUMBER_OF_GENES * firstParentPercentage);
        int numberOfSecondParentGenes = Math.round(NUMBER_OF_GENES * secondParentPercentage);
        List<MapDirection> firstParentGenes = firstParent.getGenotype().getGenotype();
        List<MapDirection> secondParentGenes = secondParent.getGenotype().getGenotype();
        boolean isFirstParentStronger = numberOfFirstParentGenes > numberOfSecondParentGenes;
        if ((isFirstParentStronger && sideOfStrongerParent == 0) || (!isFirstParentStronger && sideOfStrongerParent == 1)) {
            for (int i = 0; i < numberOfFirstParentGenes; i++) {
                childGenotype.add(i, firstParentGenes.get(i));
            }
            for (int j = numberOfFirstParentGenes; j < NUMBER_OF_GENES; j++) {
                childGenotype.add(j, secondParentGenes.get(j));
            }
        } else {
            for (int i = 0; i < numberOfSecondParentGenes; i++) {
                childGenotype.add(i, secondParentGenes.get(i));
            }
            for (int j = numberOfSecondParentGenes; j < NUMBER_OF_GENES; j++) {
                childGenotype.add(j, firstParentGenes.get(j));
            }
        }
        // mamy genotyp bez mutacji
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
