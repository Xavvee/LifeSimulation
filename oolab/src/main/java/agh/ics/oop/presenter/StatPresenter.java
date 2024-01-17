package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class StatPresenter {
    @FXML
    private Text numberOfAnimals;
    @FXML
    private Text numberOfGrasses;
    @FXML
    private Text numberOfFreePlaces;
    void showStat(Simulation simulation) {
        numberOfAnimals.setText(String.valueOf(simulation.getAnimals().size()));
        numberOfGrasses.setText(String.valueOf(simulation.getMap().getListOfGrasses().size()));
        numberOfFreePlaces.setText(String.valueOf(simulation.getMap().getNumberOfFreeHexes()));
    }
}
