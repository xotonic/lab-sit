/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xotonic.lab.sit.vehicle;

/**
 * @author User
 */
public abstract class Factory extends BasicBehavior {


    protected Habitat habitat;

    public Factory(Habitat habitat) {
        this.habitat = habitat;
        habitat.getFactories().add(this);
    }

    abstract public Vehicle create();

    abstract public void build();

}
