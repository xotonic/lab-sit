package com.xotonic.lab.sit;

import com.xotonic.lab.sit.vehicle.Behavior;
import com.xotonic.lab.sit.vehicle.Habitat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

/**
 * Created by xotonic on 01.10.2016.
 */
public class SimulationTimer {

    static Logger log = LogManager.getLogger(Form.class.getName());

    Timer timer;
    Habitat target;
    boolean started = false;
    int delay = 30;
    long simulationTime = 0;
    long simulationStartTime = -1;

    public SimulationTimer() {
        timer = new Timer(delay, null);
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
            this.target.update(simulationTime);
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

}
