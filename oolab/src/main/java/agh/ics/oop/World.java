package agh.ics.oop;

import agh.ics.oop.model.Genotype.MinorCorrectionGenotypeFactory;
import agh.ics.oop.model.Genotype.RandomGenotypeFactory;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.InflowsAndOutflows;

public class World {
    public static void main(String[] args){
        AbstractWorldMap map = new InflowsAndOutflows(20, 10,30,15,2,20,2,4,16, new RandomGenotypeFactory());
        Simulation simulation = new Simulation(map, 5, 10, 7, new MinorCorrectionGenotypeFactory());
        simulation.simulateXDays(10);
    }
}

