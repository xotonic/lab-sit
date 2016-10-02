/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xotonic.lab.sit;

import com.xotonic.lab.sit.vehicle.CarFactory;
import com.xotonic.lab.sit.vehicle.SimpleHabitat;

/**
 * @author User
 */
public class App {
    public static void main(String[] args) {


        SimpleHabitat habitat = new SimpleHabitat();
        CarFactory carFactory = new CarFactory(habitat);
        carFactory.build();
        carFactory.build();

        habitat.start();

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
                habitat.update(System.currentTimeMillis());
            } catch (Exception e) {
                System.err.println("[ERROR]:");
                e.printStackTrace();
                break;
            }
        }

        habitat.stop();
    }
}
