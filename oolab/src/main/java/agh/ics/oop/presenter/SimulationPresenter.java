package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import agh.ics.oop.model.Map.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SimulationPresenter {

    int CELL_WIDTH = 50;
    int CELL_HEIGHT = 50;
    private WorldMap map;

    @FXML
    private Label infoLabel;
    @FXML
    private Label movementDescriptionLabel;
    @FXML
    private TextField initialAnimal;
    @FXML
    private TextField initialGrass;
    @FXML
    private TextField animalEnergy;
    @FXML
    private TextField genomeLength;
    @FXML
    private TextField height;
    @FXML
    private TextField width;
    @FXML
    private TextField dailyNumberOfGrasses;
    @FXML
    private TextField minimumNumberOfMutations;
    @FXML
    private TextField maximumNumberOfMutations;
    private SimulationEngine simulationEngine;
    private ExecutorService threadPool;

    @FXML
    public void initialize() {
        initialAnimal.setText("2");
        initialAnimal.setTextFormatter(nonNegativeNumberFormatter());
        animalEnergy.setText("5");
        animalEnergy.setTextFormatter(nonNegativeNumberFormatter());
        initialGrass.setText("4");
        initialGrass.setTextFormatter(nonNegativeNumberFormatter());
        dailyNumberOfGrasses.setText("2");
        dailyNumberOfGrasses.setTextFormatter(nonNegativeNumberFormatter());
        height.setText("5");
        height.setTextFormatter(nonNegativeNumberFormatter());
        width.setText("5");
        width.setTextFormatter(nonNegativeNumberFormatter());
        genomeLength.setText("10");
        genomeLength.setTextFormatter(nonNegativeNumberFormatter());
        minimumNumberOfMutations.setText("2");
        minimumNumberOfMutations.setTextFormatter(nonNegativeNumberFormatter());
        maximumNumberOfMutations.setText("8");
        maximumNumberOfMutations.setTextFormatter(nonNegativeNumberFormatter());

    }
    private TextFormatter nonNegativeNumberFormatter() {
        return new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d+"))
                return null;
            else
                return c;
        }
        );
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public void setSimulationEngine(SimulationEngine simulationEngine) {
        this.simulationEngine = simulationEngine;
    }

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    private StartConfigurations setConfigurations(){
        StartConfigurations configurations = new StartConfigurations();
        for(TextField textField : List.of(
                initialAnimal, initialGrass, animalEnergy, dailyNumberOfGrasses,height,width,
                genomeLength,maximumNumberOfMutations,minimumNumberOfMutations)) {
            configurations.put(textField.getId(), textField.getText());
        }
        return configurations;
    }
    public void onSimulationStartClicked() {
        MultipleSimulationPresenter multipleSimulationPresenter = new MultipleSimulationPresenter();
        Stage simulationStage = new Stage();
        try {
            FXMLLoader loaderMulti = new FXMLLoader();
            loaderMulti.setLocation(getClass().getClassLoader().getResource("multiplesimulation.fxml"));
            BorderPane viewRoot = loaderMulti.load();
            threadPool = Executors.newFixedThreadPool(4);
            this.setThreadPool(threadPool);
            multipleSimulationPresenter = loaderMulti.getController(); // Pobierz kontroler z załadowanego widoku
            multipleSimulationPresenter.startMultipleSimulation(setConfigurations()); // Rozpocznij symulację w nowym oknie
            Scene scene = new Scene(viewRoot);
            simulationStage.setScene(scene);
            simulationStage.setTitle("Simulation");
            simulationStage.show();

            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
