/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xotonic.lab.sit.vehicle;

import java.util.ArrayList;
import java.util.Collection;

/** Единственная реализация класса среды */
public class SimpleHabitat extends Habitat {

    private ArrayList<Vehicle> vehicles;
    private Collection<Factory> factories;


    public SimpleHabitat() {
        vehicles = new ArrayList<>();
        factories = new ArrayList<>();
    }

    /** Обновляем все
     * @param world*/
    @Override
    public void update(World world) {

        for (Factory f : factories)
            f.update(world);


        for (Vehicle v : vehicles)
            if (v.isStarted()) {
                v.update(world);
            }
            else
                v.start();

        synchronized (vehicles) {
            vehicles.sort((o1, o2) ->
                    o1.getY() > o2.getY() ? 1 : o1.getY() == o2.getY() ? 0 : -1);
        }
    }

    /** Запускаем все */
    @Override
    public void start() {
        log.debug("SimpleHabitat start ...");

        factories.forEach(Behavior::start);

        vehicles.forEach(Vehicle::start);

    }

    /** Останавливаем все */
    @Override
    public void stop() {
        log.debug("SimpleHabitat stop ...");

        factories.forEach(Behavior::stop);

        vehicles.forEach(Vehicle::stop);

    }

    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    public Collection<Factory> getFactories() {
        return factories;
    }


    @Override
    public void reset() {
        log.debug("Reset");
        vehicles.clear();
    }

}
