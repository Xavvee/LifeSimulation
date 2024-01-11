package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenotype extends AbstractGenotype{

    public RandomGenotype(){
        super();
    }

    @Override
    public List<MapDirection> mutate(Animal firstParent, Animal secondParent) {
        super.mutate(firstParent, secondParent);
        Random random = new Random();
        int numberOfMutations = random.nextInt(MINIMAL_NUMBER_OF_MUTATIONS, MAXIMUM_NUMBER_OF_MUTATIONS+1);
        List<Integer> indexesToChange = new ArrayList<>();
        while( indexesToChange.size()<numberOfMutations){
            int randomIndex = random.nextInt(NUMBER_OF_GENES);
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
