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
public abstract class Vehicle extends BasicBehavior {

    protected ResourceId resourceId = ResourceId.DEFAULT;
    protected ResourceId resourceIdWhenMovingBack = ResourceId.DEFAULT;
    protected boolean isMovingBack = false;
    private float x = 0f;
    private float y = 0f;
    private boolean isStarted = false;

    public Vehicle(String id, float x, float y) {
        this(id);
        this.x = x;
        this.y = y;
    }

    public Vehicle(String id) {
        setId(id);
    }

    public boolean isMovingBack() {
        return isMovingBack;
    }

    public void setMovingBack(boolean movingBack) {
        isMovingBack = movingBack;
    }

    public ResourceId getResourceId() {
        return !isMovingBack ? resourceIdWhenMovingBack : resourceId;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void stop() {
        isStarted = false;
    }

    @Override
    public void start() {
        isStarted = true;
    }


    public boolean isStarted() {
        return isStarted;
    }
}
