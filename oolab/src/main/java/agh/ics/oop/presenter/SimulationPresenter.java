package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import agh.ics.oop.model.Map.MapFactory;
import agh.ics.oop.model.Map.WorldMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static agh.ics.oop.model.Map.MapFactory.listFactory;
import static agh.ics.oop.presenter.StartConfigurations.newListOfSampleConfigurations;


public class SimulationPresenter {

    int CELL_WIDTH = 50;
    int CELL_HEIGHT = 50;
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
    @FXML
    private ComboBox<String> nameOfMapType;
//    @FXML
//    private ComboBox<GenomeFactory> nameOfGenomeType;


    @FXML
    private void initialize() {
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
        nameOfMapType.getItems().addAll(listFactory.stream().map(MapFactory::getType)
                .collect((Collectors.toList())));
        nameOfMapType.getSelectionModel().selectFirst();
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
    private List<TextField> listOfTextFields(){
        return List.of(
                initialAnimal, initialGrass, animalEnergy, dailyNumberOfGrasses,height,width,
                genomeLength,maximumNumberOfMutations,minimumNumberOfMutations);
    }
    private StartConfigurations setConfigurations(){
        StartConfigurations configurations = new StartConfigurations();
        for(TextField textField : listOfTextFields()) {
            configurations.put(textField.getId(), textField.getText());
        }
        for (ComboBox<String> comboBox : List.of(nameOfMapType)) {
            configurations.put(comboBox.getId(), comboBox.getValue());
        }
        return configurations;
    }
    public void setTextField(StartConfigurations startConfigurations){
        for(TextField textField : listOfTextFields())  {
            textField.setText(startConfigurations.get(textField.getId()));
        }
        for(ComboBox<String> comboBox:List.of(nameOfMapType/*, nameOfGenotypeType*/)){
            comboBox.getSelectionModel().select(startConfigurations.get(comboBox.getId()));
        }
    }

    public void onSimulationStartClicked() {
        MultipleSimulationPresenter multipleSimulationPresenter;
        Stage simulationStage = new Stage();
        try {
            FXMLLoader loaderMulti = new FXMLLoader();
            loaderMulti.setLocation(getClass().getClassLoader().getResource("multiplesimulation.fxml"));
            BorderPane viewRoot = loaderMulti.load();
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

    public void loadConfiguration() {
        SampleConfigurationChoicePresenter sampleConfigurationChoicePresenter;
        Stage configurationStage = new Stage();
        try {
            FXMLLoader loaderMulti = new FXMLLoader();
            loaderMulti.setLocation(getClass().getClassLoader().getResource("chooseConfiguration.fxml"));
            GridPane viewRoot = loaderMulti.load();
            sampleConfigurationChoicePresenter = loaderMulti.getController(); // Pobierz kontroler z załadowanego widoku
            sampleConfigurationChoicePresenter.drawConfiguration(newListOfSampleConfigurations(), configurationStage,this);
            Scene scene = new Scene(viewRoot);
            configurationStage.setScene(scene);
            configurationStage.setTitle("Configuration choice");
            configurationStage.show();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
