package agh.ics.oop.model.Map;


import agh.ics.oop.model.DirectedPosition;
import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Genotype.GenotypeType;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2d;

import java.util.Map;


public class Globe extends AbstractWorldMap{


    public Globe(int height, int width, int numberOfGrasses, int numberOfAnimals, int dailyNumberOfGrasses, int startingEnergy, int minimumNumberOfMutations, int maximumNumberOfMutations, int genomeLength, GenotypeType genotypeType){
        super(height, width, numberOfGrasses, numberOfAnimals, dailyNumberOfGrasses, startingEnergy, minimumNumberOfMutations, maximumNumberOfMutations, genomeLength, genotypeType, MapType.GLOBE);
    }

    @Override
    public Map<Vector2d, Water> getWaters() {
        return null;
    }

    @Override
    public DirectedPosition computeMove(DirectedPosition directedPosition) {
        Vector2d step = directedPosition.getDirection().toUnitVector();
        Vector2d newPosition = directedPosition.getPosition().add(step);
        if (newPosition.getY() >= height || newPosition.getY() < 0) {
            return new DirectedPosition(directedPosition.getPosition(), directedPosition.getDirection().opposite());
        }
        else if (newPosition.getX() >= width || newPosition.getX() < 0) {
            newPosition = new Vector2d((newPosition.getX() + width) % width, newPosition.getY());
            return new DirectedPosition(newPosition,directedPosition.getDirection());
        }
        return new DirectedPosition(newPosition, directedPosition.getDirection());
    }
}
