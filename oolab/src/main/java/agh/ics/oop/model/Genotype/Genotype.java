package agh.ics.oop.model.Genotype;

import agh.ics.oop.model.Elements.Animal;
import agh.ics.oop.model.MapDirection;

import java.util.List;

public interface Genotype {

    /**
    @return generated genotype for starting animal
     **/
    List<MapDirection> generateGenotype();

    /**
     * @param firstParent is object of the first parent with its energy and genotype
     * @param secondParent is similar to firstParent
     * @return altered list of parents' genes
     */
    List<MapDirection> mutate(Animal firstParent, Animal secondParent);

    /**
     * @return genotype of given animal
     */
    List<MapDirection> getGenotype();


    int getGenomeLength();


    int getMaximumNumberOfMutations();
    int getMinimumNumberOfMutations();
}
