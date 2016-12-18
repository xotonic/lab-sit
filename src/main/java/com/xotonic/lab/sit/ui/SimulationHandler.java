package com.xotonic.lab.sit.ui;

import com.xotonic.lab.sit.vehicle.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;


public class SimulationHandler {

    private static Logger log = LogManager.getLogger(Form.class.getName());

    private Habitat habitat;
    private MutableWorld world;
    private AIManager bikeAIManager;
    private AIManager carAIManager;

    private Canvas canvas;
    private boolean started = false;
    private int delay = 1;
    private long simulationTime = 0;
    private long simulationStartTime = -1;

    private Thread logicThread;
    private Thread renderThread;
    private Thread carAIThread;
    private Thread bikeAIThread;


    public SimulationHandler() {
        bikeAIManager = new AIManager(VehicleType.bike);
        carAIManager = new AIManager(VehicleType.car);
    }

    private void createThreads() {
        logicThread = new Thread(() -> {
            while (started) {
                updateWorld();
                habitat.update(world);
                //waitForRenderDone();
            }
        });

        renderThread = new Thread(() ->
        {
            while (started) {
                canvas.update(world);
                //notifyRenderDone();
            }
        });

        bikeAIThread = new Thread(() -> {
            while (started) {
                bikeAIManager.update(world);
            }
        });

        carAIThread = new Thread(() -> {
            while (started) {
                carAIManager.update(world);
            }
        });
    }

    private void updateWorld() {
        if (simulationStartTime == -1)
            simulationStartTime = System.currentTimeMillis();
        simulationTime = System.currentTimeMillis() - simulationStartTime;
        world.setTimeMillis(simulationTime);
        world.setAreaWidth(canvas.getWidth());
        world.setAreaHeight(canvas.getHeight());
    }

    private void notifyRenderDone() {
        synchronized (ThreadLock.vehiclesRendered) {
            ThreadLock.vehiclesRendered.hookNotify().notify();
        }
    }

    private void waitForRenderDone() {

        synchronized (ThreadLock.vehiclesRendered) {
            try {
                ThreadLock.vehiclesRendered.hookWait().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public long getSimulationTime() {
        return simulationTime;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;

    }

    public void setDelay(int delay) {
        log.debug("Set delay {} ms", delay);
        this.delay = delay;
    }

    public void start() {
        log.debug("Start");
        if (!started) {
            createThreads();

            Collection<Vehicle> vehicles = habitat.getVehicles();
            canvas.setVehicles(vehicles);
            bikeAIManager.setVehicles(vehicles);
            carAIManager.setVehicles(vehicles);
            habitat.start();
            canvas.start();
            started = true;
            startThreads();

        } else {
            log.warn("Already started");
        }
    }

    private void startThreads() {
        logicThread.start();
        renderThread.start();
        bikeAIThread.start();
        carAIThread.start();
    }


    public void reset() {
        log.debug("Reset");
        if (started) {
            started = false;
            joinThreads();
            habitat.reset();
            habitat.stop();
            canvas.stop();
            simulationTime = 0;
            simulationStartTime = -1;
        } else log.warn("Already stopped");
    }

    private void joinThreads() {
        try {
            logicThread.join();
            bikeAIThread.join();
            carAIThread.join();
            renderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setWorld(MutableWorld world) {
        this.world = world;
    }

    public SimulationHandler setCanvas(Canvas canvas) {
        this.canvas = canvas;
        return this;
    }


}
