package agh.ics.oop.presenter;

import java.util.List;

public class SampleConfigurationChoicePresenter {
    SimulationPresenter simulationPresenter;
    List<StartConfigurations> listOfStartConfigurations;
    public void drawConfiguration(List<StartConfigurations> listOfStartConfigurations, SimulationPresenter simulationPresenter){
        this.simulationPresenter = simulationPresenter;
        this.listOfStartConfigurations = listOfStartConfigurations;
    }
    public void onLoadClick() {
        simulationPresenter.setTextField(listOfStartConfigurations.get(0));
    }
    public void onCancelClick() {

    }
}
