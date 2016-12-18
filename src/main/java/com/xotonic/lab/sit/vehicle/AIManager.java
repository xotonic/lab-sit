package com.xotonic.lab.sit.vehicle;


import java.util.Collection;

public class AIManager extends BasicBehavior {

    private final VehicleType vehicleType;
    private Collection<Vehicle> vehicles;
    private boolean isStarted;

    public AIManager(VehicleType type) {
        vehicleType = type;
    }

    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public void start() {
        isStarted = true;
    }

    @Override
    public void update(World world) {

        synchronized (vehicles) {
            vehicles.stream()
                    .filter(ai -> ai.getType() == vehicleType && ai instanceof ThinkingVehicle)
                    .forEach(ai -> ((ThinkingVehicle) ai).processAI(world));
        }
    }

    @Override
    public void stop() {
        isStarted = false;
    }

    public AIManager setVehicles(Collection<Vehicle> brains) {
        this.vehicles = brains;
        return this;
    }
}
