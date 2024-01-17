package agh.ics.oop.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.List;

public class SampleConfigurationChoicePresenter {
    @FXML
    ComboBox<StartConfigurations> choice;
    SimulationPresenter simulationPresenter;
    List<StartConfigurations> listOfStartConfigurations;
    Stage configurateStage;
    public void drawConfiguration(List<StartConfigurations> listOfStartConfigurations,
                                  Stage configurationStage, SimulationPresenter simulationPresenter){
        this.simulationPresenter = simulationPresenter;
        this.listOfStartConfigurations = listOfStartConfigurations;
        this.configurateStage = configurationStage;
        choice.getItems().addAll(listOfStartConfigurations);
    }
    public void onLoadClick() {
        StartConfigurations startConfigurations = choice.getValue();
        if (startConfigurations != null){
            simulationPresenter.setTextField(startConfigurations);
            configurateStage.close();
        }
    }
    public void onCancelClick() {
        configurateStage.close();
    }
}
