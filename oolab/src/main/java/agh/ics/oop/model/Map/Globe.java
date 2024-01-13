package agh.ics.oop.model.Map;


import agh.ics.oop.model.Elements.Water;
import agh.ics.oop.model.Vector2d;

import java.util.Map;


public class Globe extends AbstractWorldMap{


    public Globe(int height, int width, int numberOfGrasses, int numberOfAnimals, int dailyNumberOfGrasses){
        super(height, width, numberOfGrasses, numberOfAnimals, dailyNumberOfGrasses, );
    }

    @Override
    public Map<Vector2d, Water> getWaters() {
        return null;
    }


}
