/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xotonic.lab.sit.vehicle;

import java.util.Collection;

/**
 * @author User
 */
public interface Painter extends Behavior {

    void onRepaint(Collection<Vehicle> vehicles);
}
