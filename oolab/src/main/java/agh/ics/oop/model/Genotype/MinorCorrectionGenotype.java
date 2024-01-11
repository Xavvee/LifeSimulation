package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinorCorrectionGenotype extends AbstractGenotype {

    public MinorCorrectionGenotype(){
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
        int[] possibleValues = {-1, 1};
        for(int index : indexesToChange){
            int drawnValue = possibleValues[random.nextInt(2)];
            MapDirection gene = childGenotype.get(index);
            if(drawnValue == -1){
                gene = gene.previous();
            } else{
                gene = gene.next();
            }
            childGenotype.set(index, gene);
        }
        return childGenotype;
    }
}
