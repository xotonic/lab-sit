package com.xotonic.lab.sit.ui;

import com.xotonic.lab.sit.settings.ai.AISettingsController;
import com.xotonic.lab.sit.settings.ai.AISettingsView;
import com.xotonic.lab.sit.vehicle.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;


public class SimulationHandler implements AISettingsView<AISettingsController> {

    private static Logger log = LogManager.getLogger(Form.class.getName());
    private final AtomicBoolean isBikeAiStarted = new AtomicBoolean();
    private final AtomicBoolean isCarAiStarted = new AtomicBoolean();
    private Habitat habitat;
    private MutableWorld world;
    private AIManager bikeAIManager;
    private AIManager carAIManager;
    private Canvas canvas;
    private boolean started = false;
    private int delay = 20;
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
                sleep();
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
                {
                    if (isBikeAiStarted.get())
                        bikeAIManager.update(world);
                    else {
                        try {
                            synchronized (isBikeAiStarted) {
                                isBikeAiStarted.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        carAIThread = new Thread(() -> {
            while (started) {
                {
                    if (isCarAiStarted.get())
                        carAIManager.update(world);
                    else {
                        try {
                            synchronized (isCarAiStarted) {
                                isCarAiStarted.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void sleep() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            notifyBikeAI();
            bikeAIThread.join();
            notifyCarAi();
            carAIThread.join();
            renderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void notifyCarAi() {
        synchronized (isCarAiStarted) {
            isCarAiStarted.notifyAll();
        }
    }

    private void notifyBikeAI() {
        synchronized (isBikeAiStarted) {
            isBikeAiStarted.notifyAll();
        }
    }

    public void setWorld(MutableWorld world) {
        this.world = world;
    }

    public SimulationHandler setCanvas(Canvas canvas) {
        this.canvas = canvas;
        return this;
    }


    @Override
    public void OnBikeThreadPriorityChanged(int priority) {
        if (started)
            bikeAIThread.setPriority(priority);
    }

    @Override
    public void OnCarThreadPriorityChanged(int priority) {
        if (started)
            carAIThread.setPriority(priority);

    }

    @Override
    public void OnCarAIToggled(boolean on) {
        if (started) {
            isCarAiStarted.set(on);

            if (on) {
                notifyCarAi();
            }
        }

    }

    @Override
    public void OnBikeAIToggled(boolean on) {
        if (started) {
            isBikeAiStarted.set(on);

            if (on) {
                notifyBikeAI();
            }
        }

    }

    @Override
    public void setController(AISettingsController controller) {
        createThreads();

        controller.setBikeAIToggled(isBikeAiStarted.get());
        controller.setCarAIToggled(isCarAiStarted.get());
        controller.setBikeThreadPriority(bikeAIThread.getPriority());
        controller.setCarThreadPriority(carAIThread.getPriority());
    }
}
