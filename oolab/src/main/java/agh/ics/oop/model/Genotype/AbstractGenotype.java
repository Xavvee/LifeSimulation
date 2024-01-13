package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractGenotype implements Genotype {
    protected final int genomeLength;
    protected final int minimumNumberOfMutations;
    protected final int maximumNumberOfMutations;
    protected List<MapDirection> childGenotype;
    protected List<MapDirection> genotype;
    public AbstractGenotype(int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations){
        this.genomeLength = genomeLength;
        this.minimumNumberOfMutations = minimumNumberOfMutations;
        this.maximumNumberOfMutations = maximumNumberOfMutations;
        this.genotype = generateGenotype();
    }

    public AbstractGenotype(int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations, List<MapDirection> genotype){
        this(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations);
        this.genotype = genotype;
    }

    @Override
    public List<MapDirection> mutate(Animal firstParent, Animal secondParent) {
        childGenotype = new ArrayList<>();
        Random random = new Random();
        // if sideOfStrongerParent == 0 then it's left, else it's right
        int sideOfStrongerParent = random.nextInt(2);
        float firstParentPercentage = (float) firstParent.getEnergy() /(secondParent.getEnergy() + firstParent.getEnergy());
        float secondParentPercentage = (float) secondParent.getEnergy() /(secondParent.getEnergy() + firstParent.getEnergy());
        int numberOfFirstParentGenes = Math.round(genomeLength * firstParentPercentage);
        int numberOfSecondParentGenes = Math.round(genomeLength * secondParentPercentage);
        List<MapDirection> firstParentGenes = firstParent.getGenotype().getGenotype();
        List<MapDirection> secondParentGenes = secondParent.getGenotype().getGenotype();
        boolean isFirstParentStronger = numberOfFirstParentGenes > numberOfSecondParentGenes;
        if ((isFirstParentStronger && sideOfStrongerParent == 0) || (!isFirstParentStronger && sideOfStrongerParent == 1)) {
            for (int i = 0; i < numberOfFirstParentGenes; i++) {
                childGenotype.add(i, firstParentGenes.get(i));
            }
            for (int j = numberOfFirstParentGenes; j < genomeLength; j++) {
                childGenotype.add(j, secondParentGenes.get(j));
            }
        } else {
            for (int i = 0; i < numberOfSecondParentGenes; i++) {
                childGenotype.add(i, secondParentGenes.get(i));
            }
            for (int j = numberOfSecondParentGenes; j < genomeLength; j++) {
                childGenotype.add(j, firstParentGenes.get(j));
            }
        }
        // mamy genotyp bez mutacji
        return childGenotype;
    }

    @Override
    public List<MapDirection> generateGenotype() {
        List<MapDirection> genotype = new ArrayList<>();
        for(int i = 0; i < genomeLength; i++){
            int randomNumber = (int)Math.floor(Math.random() * (8));
            genotype.add(MapDirection.values()[randomNumber]);
        }
        return genotype;
    }



    @Override
    public List<MapDirection> getGenotype() {
        return genotype;
    }


    public int getGenomeLength() {
        return genomeLength;
    }

    public int getMaximumNumberOfMutations() {
        return maximumNumberOfMutations;
    }

    public int getMinimumNumberOfMutations() {
        return minimumNumberOfMutations;
    }
}
