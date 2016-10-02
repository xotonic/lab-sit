/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xotonic.lab.sit.vehicle;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleHabitat extends Habitat {

    private Collection<Vehicle> vehicles;
    private Collection<Factory> factories;
    private Collection<Painter> painters;

    public SimpleHabitat() {
        vehicles = new ArrayList<>();
        factories = new ArrayList<>();
        painters = new ArrayList<>();
    }

    @Override
    public void update(long timeMillis) {
        log.trace("SimpleHabitat update ...");

        for (Factory f : factories)
            f.update(timeMillis);

        for (Vehicle v : vehicles)
            if (v.isStarted())
                v.update(timeMillis);
            else
                v.start();

        for (Painter p : painters) {
            p.update(timeMillis);
            p.onRepaint(vehicles);
        }

    }

    @Override
    public void start() {
        log.debug("SimpleHabitat start ...");

        factories.forEach(Behavior::start);

        vehicles.forEach(Vehicle::start);

        painters.forEach(Behavior::start);
    }

    @Override
    public void stop() {
        log.debug("SimpleHabitat stop ...");

        factories.forEach(Behavior::stop);

        vehicles.forEach(Vehicle::stop);

        painters.forEach(Behavior::stop);
    }

    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    public Collection<Factory> getFactories() {
        return factories;
    }

    public Collection<Painter> getPainters() {
        return painters;
    }

    @Override
    public void reset() {
        log.debug("Reset");
        vehicles.clear();
    }

}
