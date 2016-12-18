package com.xotonic.lab.sit.ui;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum ThreadLock {
    vehiclesRendered,
    carAIResumed,
    bikeAIResumed;

    private static Logger log = LogManager.getLogger(ThreadLock.class.getName());

    private boolean isWaiting = false;
    private int notifyNo = 0;
    private int waitNo = 0;

    public ThreadLock hookNotify() {
        log.debug("Notify for {} No:{}", name(), notifyNo);
        notifyNo++;
        isWaiting = false;
        return this;
    }

    public ThreadLock hookWait() {
        log.debug("Wait for {} No:{}", name(), waitNo);
        waitNo++;
        isWaiting = true;
        return this;
    }

    public synchronized boolean isWaiting() {
        return isWaiting;
    }
}
