/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xotonic.lab.sit.vehicle.bike;

import com.xotonic.lab.sit.ui.ResourceId;
import com.xotonic.lab.sit.vehicle.ThinkingVehicle;
import com.xotonic.lab.sit.vehicle.VehicleType;
import com.xotonic.lab.sit.vehicle.World;

/**
 * @author User
 */
public class Bike extends ThinkingVehicle {


    protected ResourceId resourceId = ResourceId.BIKE;
    protected ResourceId resourceIdWhenMovingBack = ResourceId.BIKE_BACK;

    public Bike(String id) {
        super(id);
    }

    @Override
    public ResourceId getResourceId() {
        return !isMovingBack ? resourceIdWhenMovingBack : resourceId;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.bike;
    }


    @Override
    public void update(World world) {

    }
}
