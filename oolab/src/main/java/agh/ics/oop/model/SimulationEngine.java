package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> simulationThreads;
    private final ExecutorService threadPool;
    public SimulationEngine(List<Simulation> simulations){
        this.simulations = simulations;
        this.simulationThreads = new ArrayList<>();
        this.threadPool = Executors.newFixedThreadPool(4);
    }

    public void runSync(int days){
        for( Simulation simulation : simulations){
            simulation.simulateXDays(days);
        }
    }

    public void runAsync(int days) {
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(() ->simulation.simulateXDays(days));
            simulationThreads.add(thread);
            thread.start();
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        for (Thread thread:simulationThreads) {
            thread.join();
        }
    }
}
