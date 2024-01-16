package agh.ics.oop.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.lang.module.Configuration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StartConfigurations extends HashMap<String, String> {
    public int getNumber(Object key) {
        return Integer.parseInt(super.get(key));
    }

    private static StartConfigurations sampleConfig1() {
        StartConfigurations configurations = new StartConfigurations();
        configurations.put("name", "Przykład 1");
        configurations.put("initialAnimal","2");
        configurations.put("animalEnergy","5");
        configurations.put("initialGrass","4");
        configurations.put("dailyNumberOfGrasses","2");
        configurations.put("height","5");
        configurations.put("width","5");
        configurations.put("genomeLength","10");
        configurations.put("minimumNumberOfMutations","2");
        configurations.put("maximumNumberOfMutations","8");
        return configurations;
    }
    private static StartConfigurations sampleConfig2() {
        StartConfigurations configurations = new StartConfigurations();
        configurations.put("name", "Przykład 2");
        configurations.put("initialAnimal","3");
        configurations.put("animalEnergy","5");
        configurations.put("initialGrass","20");
        configurations.put("dailyNumberOfGrasses","3");
        configurations.put("height","10");
        configurations.put("width","15");
        configurations.put("genomeLength","10");
        configurations.put("minimumNumberOfMutations","4");
        configurations.put("maximumNumberOfMutations","8");
        return configurations;
    }
    public String toString() {
        return this.get("name");
    }
    public static List<StartConfigurations> newListOfSampleConfigurations(){
        List<StartConfigurations> listOfSampleConfiguration = new ArrayList<>();
        listOfSampleConfiguration.add(sampleConfig1());
        listOfSampleConfiguration.add(sampleConfig2());
        return listOfSampleConfiguration;
    }
}
