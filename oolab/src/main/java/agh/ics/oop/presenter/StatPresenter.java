package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.Elements.WorldElement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Map;

public class StatPresenter {
    @FXML
    private Text numberOfAnimals;
    @FXML
    private Text numberOfGrasses;
    @FXML
    private Text numberOfFreePlaces;
    @FXML
    private GridPane objectStat;
    private WorldElement worldElement;
    public void showStat(Simulation simulation) {
        numberOfAnimals.setText(String.valueOf(simulation.getAnimals().size()));
        numberOfGrasses.setText(String.valueOf(simulation.getMap().getListOfGrasses().size()));
        numberOfFreePlaces.setText(String.valueOf(simulation.getMap().getNumberOfFreeHexes()));
        showObjectStat();
    }

    public void setCurrentChosenElement(WorldElement object) {
        this.worldElement = object;
        showObjectStat();
    }

    private void showObjectStat() {
        if (worldElement == null) {
            return;
        }
        clearStat();
        int i = 0;
        for (Map.Entry<String,String> stat : worldElement.getStat().entrySet()){
            Label labelKey = new Label(stat.getKey());
            Text textValue = new Text(stat.getValue());
            objectStat.add(labelKey,0,i);
            objectStat.add(textValue,1,i);
            i++;
        }
    }

    private void clearStat() {
        objectStat.getChildren().clear();
    }
}
