/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xotonic.lab.sit.vehicle;

import com.xotonic.lab.sit.ui.ResourceId;

/**
 * @author User
 */
public class Car extends Vehicle {


    protected ResourceId resourceId = ResourceId.CAR;

    public Car(String id) {
        super(id);
    }

    @Override
    public ResourceId getResourceId() {
        return resourceId;
    }

    @Override
    public void update(long timeMillis) {
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

}
