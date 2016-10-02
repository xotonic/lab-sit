package com.xotonic.lab.sit.vehicle;

import java.util.Random;


public class BikeFactory extends TimedLuckyFactory {

    private Random r = new Random();

    public BikeFactory(Habitat habitat) {
        super(habitat);
        cooldown = 200;
        setCreateChance(0.2f);
    }

    @Override
    public Vehicle create() {
        Bike bike = new Bike(Bike.class.getSimpleName() + "-" + getNextId());

        bike.setX(r.nextFloat() * habitat.getWorldWidth());
        bike.setY(r.nextFloat() * habitat.getWorldHeight());
        log.debug("Created car {}", bike.getId());
        return bike;
    }

}
