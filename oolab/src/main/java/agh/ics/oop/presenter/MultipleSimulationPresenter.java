package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import agh.ics.oop.model.Map.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

public class MultipleSimulationPresenter  implements MapChangeListener {
    private WorldMap map;
    int CELL_WIDTH = 50;
    int CELL_HEIGHT = 50;

    @FXML
    private Label movementDescriptionLabel;
    @FXML
    private GridPane mapGrid;



    public void setWorldMap(WorldMap map){
        this.map = map;
    }


    public void startMultipleSimulation(List<MoveDirection> directions) {
//        List<Vector2d> positions = List.of(new Vector2d(0,0), new Vector2d(0,2));
//        GrassField map = new GrassField(4);
//        this.setWorldMap(map);
//        map.addObserver(this);
//        Simulation simulation = new Simulation(positions, directions, map);
//        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
//        simulationEngine.runAsyncInThreadPool();
    }

    public void drawMap(WorldMap worldMap){
        clearGrid();
        Label yx = new Label("y/x");
        mapGrid.add(yx, 0, 0);
        GridPane.setHalignment(yx, HPos.CENTER);
        for (int k = 0; k <= worldMap.getCurrentBounds().upperRight().getX() - worldMap.getCurrentBounds().lowerLeft().getX(); k++) {
            Label label = new Label("" + (worldMap.getCurrentBounds().lowerLeft().getX() + k));
            mapGrid.add(label, k + 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for (int k = 0; k <= worldMap.getCurrentBounds().upperRight().getY() - worldMap.getCurrentBounds().lowerLeft().getY(); k++) {
            Label label = new Label("" + (worldMap.getCurrentBounds().upperRight().getY() - k));
            mapGrid.add(label, 0, k + 1);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));

        for (int i = 0; i <= worldMap.getCurrentBounds().upperRight().getX() - worldMap.getCurrentBounds().lowerLeft().getX(); i++) {
            for (int j = 0; j <= worldMap.getCurrentBounds().upperRight().getY() - worldMap.getCurrentBounds().lowerLeft().getY(); j++) {
                Vector2d curMapPos = new Vector2d(worldMap.getCurrentBounds().lowerLeft().getX() + i, worldMap.getCurrentBounds().upperRight().getY() - j);
                if (worldMap.objectAt(curMapPos) != null) {
                    String object = worldMap.objectAt(curMapPos).toString();
                    mapGrid.add(new Label(object), i + 1, j + 1);
                    GridPane.setHalignment(mapGrid.getChildren().get(mapGrid.getChildren().size() - 1), HPos.CENTER);
                }
            }
        }

    }


    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(()->{
            drawMap(worldMap);
            this.movementDescriptionLabel.setText(message);
        });
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

}
