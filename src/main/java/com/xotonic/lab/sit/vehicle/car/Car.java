/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xotonic.lab.sit.vehicle.car;

import com.xotonic.lab.sit.ui.ResourceId;
import com.xotonic.lab.sit.vehicle.ThinkingVehicle;

/**
 * @author User
 */
public class Car extends ThinkingVehicle {


    protected ResourceId resourceId = ResourceId.CAR;
    protected ResourceId resourceIdWhenMovingBack = ResourceId.CAR_BACK;

    public Car(String id) {
        super(id);
    }

    @Override
    public ResourceId getResourceId() {
        return !isMovingBack ? resourceIdWhenMovingBack : resourceId;
    }


}
