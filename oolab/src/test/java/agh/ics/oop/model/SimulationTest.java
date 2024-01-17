package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.Genotype.MinorCorrectionGenotypeFactory;
import agh.ics.oop.model.Genotype.RandomGenotypeFactory;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.Globe;
import agh.ics.oop.model.Map.InflowsAndOutflows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationTest {

    @Test
    public void simulateOneDayTest(){
        //given
        AbstractWorldMap map = new Globe(15,25,30,8,4,30,2,4,10, new RandomGenotypeFactory());
        Simulation simulation = new Simulation(map, 20, 10, 8, new MinorCorrectionGenotypeFactory());
        //when
        int numberOfGrasses = simulation.getMap().getListOfGrasses().size();
        int numberOfAnimals = simulation.getMap().getListOfAnimals().size();
        System.out.println(numberOfGrasses);
        System.out.println(numberOfAnimals);
        simulation.simulateOneDay();
        numberOfGrasses = simulation.getMap().getListOfGrasses().size();
        numberOfAnimals = simulation.getMap().getListOfAnimals().size();
        System.out.println(numberOfGrasses);
        System.out.println(numberOfAnimals);
        //then
    }


    @Test
    public void starvationTest(){
        //given
        AbstractWorldMap map = new Globe(15,25,0,8,0,20,2,4,10, new RandomGenotypeFactory());
        Simulation simulation = new Simulation(map, 0, 25, 20, new RandomGenotypeFactory());
        //when
        int numberOfGrasses = simulation.getMap().getListOfGrasses().size();
        int numberOfAnimals = simulation.getMap().getListOfAnimals().size();
        System.out.println(numberOfGrasses);
        System.out.println(numberOfAnimals);
        simulation.simulateXDays(21);
        numberOfGrasses = simulation.getMap().getListOfGrasses().size();
        numberOfAnimals = simulation.getMap().getListOfAnimals().size();
        System.out.println(numberOfGrasses);
        System.out.println(numberOfAnimals);
        System.out.println(map);
        //then
        assertEquals(numberOfAnimals, 0);
        assertEquals(numberOfGrasses, 0);
    }
    @Test
    public void manageWatersTest(){
        //given
        AbstractWorldMap map = new InflowsAndOutflows(10,10,10,0,10,40,2,4,10, new RandomGenotypeFactory());
        Simulation simulation = new Simulation(map, 0, 25, 20, new RandomGenotypeFactory());
        //when
        assertEquals(map.getWaters().size(),16);
        System.out.println(map);
        for( int i = 1 ; i < 10 ; i++){
            simulation.simulateOneDay();
            System.out.println(map);
            System.out.println(map.getNumberOfFreeHexes());
//            System.out.println(map.getAnimals().size());
//            System.out.println(map.getWaters().size());
        }
        //then
    }

    @Test
    public void reproductionTest(){
        //given

        //when

        //then
    }

    @Test
    public void grassGrowingTest(){
        //given
        AbstractWorldMap map = new Globe(15,25,10,0,25,20,2,4,10, new RandomGenotypeFactory());
        Simulation simulation = new Simulation(map, 0, 25, 20, new RandomGenotypeFactory());
        //when
        assertEquals(10,map.getGrasses().size());
        simulation.simulateXDays(2);
        assertEquals(60,map.getGrasses().size());
        //then
        simulation.simulateXDays(20);
        assertEquals(15*25, map.getGrasses().size());
    }
    @Test
    public void consumptionTest(){
        //given
        AbstractWorldMap map = new Globe(5,5,24,1,1,20,2,4,10, new RandomGenotypeFactory());
        Simulation simulation = new Simulation(map, 10, 25, 20, new MinorCorrectionGenotypeFactory());
        //when
        simulation.simulateXDays(500);
        //then
        assertTrue( map.getListOfAnimals().get(0).getEnergy() > 20);
    }



}
