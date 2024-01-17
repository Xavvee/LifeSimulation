package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.Elements.WorldElement;
import agh.ics.oop.model.Genotype.Genotype;
import agh.ics.oop.model.Genotype.GenotypeFactory;
import agh.ics.oop.model.Map.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.List;

import static agh.ics.oop.model.Genotype.GenotypeFactory.factoryByNameGenotype;
import static agh.ics.oop.model.Map.MapFactory.factoryByNameMap;

public class MultipleSimulationPresenter  implements MapChangeListener {
    private WorldMap map;
    int CELL_WIDTH = 50;
    int CELL_HEIGHT = 50;

    @FXML
    private Label movementDescriptionLabel;
    @FXML
    private GridPane mapGrid;
    private Simulation simulation;
    private long running = 0;
    private StatPresenter statPresenter;

    public void setWorldMap(WorldMap map){
        this.map = map;
    }


    public void startMultipleSimulation( StartConfigurations startConfigurations, StatPresenter statPresenter) {
        GenotypeFactory genotypeFactory = factoryByNameGenotype.get(startConfigurations.get("nameOfGenotypeType"));
        WorldMap map = factoryByNameMap.get(startConfigurations.get("nameOfMapType")).makeMap(startConfigurations, genotypeFactory);
        this.setWorldMap(map);
        map.addObserver(this);
        this.simulation = new Simulation(map, 5, 3, 4, genotypeFactory);
        drawMap(map);
        this.statPresenter = statPresenter;
        statPresenter.showStat(this.simulation);
    }
    public synchronized void continueSimulation() {
        running++;
        long equalRunning = running;
        Thread thread = new Thread(() ->{
            while(true) {
                synchronized (this) {
                    boolean ifEqualRunning = running == equalRunning;
                    if(!ifEqualRunning) {
                        break;
                    }
                    System.out.println("symulujemy dzien");
                    simulation.simulateOneDay();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
    public synchronized void stopSimulation() {
        running ++;
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
            Label label = new Label("" + (worldMap.getCurrentBounds().upperRight().getY() - k ));
            mapGrid.add(label, 0, k + 1);
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));

        for (int i = 0; i <= worldMap.getCurrentBounds().upperRight().getX() - worldMap.getCurrentBounds().lowerLeft().getX(); i++) {
            for (int j = 0; j <= worldMap.getCurrentBounds().upperRight().getY() - worldMap.getCurrentBounds().lowerLeft().getY(); j++) {
                Vector2d curMapPos = new Vector2d(worldMap.getCurrentBounds().lowerLeft().getX() + i, worldMap.getCurrentBounds().upperRight().getY() - j );
                if (worldMap.objectAt(curMapPos) != null) {
                    WorldElement object = worldMap.objectAt(curMapPos);
                    Label label = new Label(object.toString());
                    label.setTextFill(Color.DARKBLUE);
                    label.setStyle("-fx-background-color: " + object.getColor() + ";");
                    label.setPrefHeight(Double.POSITIVE_INFINITY);
                    label.setPrefWidth(Double.POSITIVE_INFINITY);
                    label.setTextAlignment(TextAlignment.CENTER);
                    mapGrid.add(label, i + 1, j + 1);
                    GridPane.setHalignment(mapGrid.getChildren().get(mapGrid.getChildren().size() - 1), HPos.CENTER);
                }
            }
        }

    }


    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(()->{
            synchronized (worldMap) {
                drawMap(worldMap);
                statPresenter.showStat(this.simulation);
                this.movementDescriptionLabel.setText(message);
            }
        });
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

}
