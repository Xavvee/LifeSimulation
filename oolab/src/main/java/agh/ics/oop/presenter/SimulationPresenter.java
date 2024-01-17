package agh.ics.oop.presenter;

import agh.ics.oop.model.Genotype.GenotypeFactory;
import agh.ics.oop.model.Map.MapFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static agh.ics.oop.model.Genotype.GenotypeFactory.listFactoryGenotype;
import static agh.ics.oop.model.Map.MapFactory.listFactoryMap;
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
    @FXML
    private ComboBox<String> nameOfGenotypeType;


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
        nameOfMapType.getItems().addAll(listFactoryMap.stream().map(MapFactory::getType)
                .collect((Collectors.toList())));
        nameOfMapType.getSelectionModel().selectFirst();
        nameOfGenotypeType.getItems().addAll(listFactoryGenotype.stream().map(GenotypeFactory::getType)
                .collect((Collectors.toList())));
        nameOfGenotypeType.getSelectionModel().selectFirst();
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
        for (ComboBox<String> comboBox : List.of(nameOfMapType, nameOfGenotypeType)) {
            configurations.put(comboBox.getId(), comboBox.getValue());
        }
        return configurations;
    }
    public void setTextField(StartConfigurations startConfigurations){
        for(TextField textField : listOfTextFields())  {
            textField.setText(startConfigurations.get(textField.getId()));
        }
        for(ComboBox<String> comboBox:List.of(nameOfMapType, nameOfGenotypeType)){
            comboBox.getSelectionModel().select(startConfigurations.get(comboBox.getId()));
        }
    }

    public void onSimulationStartClicked() {
        MultipleSimulationPresenter multipleSimulationPresenter;
        Stage simulationStage = new Stage();
        Stage statStage = new Stage();
        try {
            FXMLLoader loaderMulti = new FXMLLoader();
            FXMLLoader loaderStats = new FXMLLoader();
            loaderMulti.setLocation(getClass().getClassLoader().getResource("multiplesimulation.fxml"));
            loaderStats.setLocation(getClass().getClassLoader().getResource("statisticshow.fxml"));
            BorderPane viewRoot = loaderMulti.load();
            GridPane viewStat = loaderStats.load();
            multipleSimulationPresenter = loaderMulti.getController(); // Pobierz kontroler z załadowanego widoku
            StatPresenter statPresenter = loaderStats.getController();
            multipleSimulationPresenter.startMultipleSimulation(setConfigurations(),statPresenter); // Rozpocznij symulację w nowym oknie
            Scene scene = new Scene(viewRoot);
            Scene sceneStat = new Scene(viewStat);
            simulationStage.setScene(scene);
            statStage.setScene(sceneStat);
            simulationStage.setTitle("Simulation");
            statStage.setTitle("Stats");
            simulationStage.show();
            statStage.show();
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
