package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import agh.ics.oop.model.Map.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SimulationPresenter implements MapChangeListener {

    int CELL_WIDTH = 50;
    int CELL_HEIGHT = 50;
    private WorldMap map;

    @FXML
    private Label infoLabel;
    @FXML
    private Label movementDescriptionLabel;
    @FXML
    private GridPane mapGrid;
    private SimulationEngine simulationEngine;
    private ExecutorService threadPool;


    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public void setSimulationEngine(SimulationEngine simulationEngine) {
        this.simulationEngine = simulationEngine;
    }

    public void setWorldMap(WorldMap map){
        this.map = map;
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
            multipleSimulationPresenter.startMultipleSimulation(); // Rozpocznij symulację w nowym oknie
            Scene scene = new Scene(viewRoot);
            simulationStage.setScene(scene);
            simulationStage.setTitle("Simulation");
            simulationStage.show();

            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

}
