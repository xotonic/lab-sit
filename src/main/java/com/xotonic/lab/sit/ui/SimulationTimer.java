package com.xotonic.lab.sit.ui;

import com.xotonic.lab.sit.vehicle.Behavior;
import com.xotonic.lab.sit.vehicle.Habitat;
import com.xotonic.lab.sit.vehicle.MutableWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;


public class SimulationTimer {

    private static Logger log = LogManager.getLogger(Form.class.getName());

    private Timer timer;
    private Habitat target;
    private MutableWorld world;
    private boolean started = false;
    private int delay = 1;
    private long simulationTime = 0;
    private long simulationStartTime = -1;
    public SimulationTimer() {
        timer = new Timer(delay, null);
    }

    public long getSimulationTime() {
        return simulationTime;
    }

    public boolean isStarted() {
        return started;
    }

    public Behavior getTarget() {
        return target;
    }

    public void setTarget(Habitat target) {
        this.target = target;
        timer.addActionListener(e -> {
            if (simulationStartTime == -1)
                simulationStartTime = System.currentTimeMillis();
            simulationTime = System.currentTimeMillis() - simulationStartTime;
            world.setTimeMillis(simulationTime);
            this.target.update(world);
        });
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        log.debug("Set delay {} ms", delay);
        this.delay = delay;
        timer.setDelay(delay);
    }

    public void start() {
        log.debug("Start");
        if (!started) {
            target.start();
            timer.start();
            started = true;
        } else log.warn("Already started");
    }

    public void pause() {
        log.debug("Pause");
        if (started) {
            timer.stop();
            target.stop();
            started = false;
        } else log.warn("Not started, but trying pause");
    }

    public void reset() {
        log.debug("Reset");
        if (started) {
            timer.stop();
            target.reset();
            target.stop();
            simulationTime = 0;
            simulationStartTime = -1;
            started = false;
        } else log.warn("Already stopped");
    }

    public void setWorld(MutableWorld world) {
        this.world = world;
    }
}
