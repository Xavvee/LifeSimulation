package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.Genotype.GenotypeType;
import agh.ics.oop.model.Map.AbstractWorldMap;
import agh.ics.oop.model.Map.Globe;
import agh.ics.oop.model.Map.MapType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationTest {

    @Test
    public void simulateOneDayTest(){
        //given
        AbstractWorldMap map = new Globe(15,25,30,8,4,30,2,4,10, GenotypeType.MINOR_CORRECTION, MapType.GLOBE);
        Simulation simulation = new Simulation(map, 20, 10, 8);
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
        AbstractWorldMap map = new Globe(15,25,0,8,0,20,2,4,10, GenotypeType.MINOR_CORRECTION, MapType.GLOBE);
        Simulation simulation = new Simulation(map, 0, 25, 20);
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

        //when

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
        AbstractWorldMap map = new Globe(15,25,10,0,25,20,2,4,10, GenotypeType.MINOR_CORRECTION, MapType.GLOBE);
        Simulation simulation = new Simulation(map, 0, 25, 20);
        //when
        System.out.println(map.getGrasses().size());
        simulation.simulateXDays(15);
        System.out.println(map.getGrasses().size());
        //then
    }
    @Test
    public void consumptionTest(){
        //given
        AbstractWorldMap map = new Globe(5,5,25,1,1,20,2,4,10, GenotypeType.MINOR_CORRECTION, MapType.GLOBE);
        Simulation simulation = new Simulation(map, 10, 25, 20);
        //when
        System.out.println(map.getListOfAnimals().get(0).getEnergy());
        simulation.simulateXDays(10);
        System.out.println(map.getListOfAnimals().get(0).getEnergy());

        //then
    }



}
