package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenotype extends AbstractGenotype{

    public RandomGenotype(int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations){
        super(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations);
    }

    public RandomGenotype(int genomeLength, int minimumNumberOfMutations, int maximumNumberOfMutations, Animal firstParent, Animal secondParent){
        super(genomeLength, minimumNumberOfMutations, maximumNumberOfMutations, firstParent, secondParent);
    }

    @Override
    public List<MapDirection> mutate(Animal firstParent, Animal secondParent) {
        super.mutate(firstParent, secondParent);
        Random random = new Random();
        int numberOfMutations = random.nextInt(minimumNumberOfMutations, maximumNumberOfMutations +1);
        List<Integer> indexesToChange = new ArrayList<>();
        while( indexesToChange.size()<numberOfMutations){
            int randomIndex = random.nextInt(genomeLength);
            if(!indexesToChange.contains(randomIndex)){
                indexesToChange.add(randomIndex);
            }
        }
        for(int index : indexesToChange){
            int randomNumber = (int)Math.floor(Math.random() * (8));
            childGenotype.set(index, MapDirection.values()[randomNumber]);
        }
        return childGenotype;
    }

}
