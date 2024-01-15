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

    public void runSync(){
        for( Simulation simulation : simulations){
            simulation.run();
        }
    }

    public void runAsync() {
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation::run);
            simulationThreads.add(thread);
            thread.start();
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        threadPool.shutdown();
        try {
            if (threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
                if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.err.println("ThreadPool did not terminate");
                }
            }
        } catch (InterruptedException ex){
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public  void runAsyncInThreadPool(int days){
        for (Simulation simulation : simulations) {
            threadPool.submit(() ->simulation.simulateXDays(days));
        }
    }


}
