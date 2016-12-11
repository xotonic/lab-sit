package com.xotonic.lab.sit.vehicle.bike;

import com.xotonic.lab.sit.vehicle.Habitat;
import com.xotonic.lab.sit.vehicle.TimedLuckyFactory;
import com.xotonic.lab.sit.vehicle.Vehicle;

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
        bike.setMovingBack(r.nextBoolean());
        bike.setAi(new BikeAI());
        log.debug("Created car {}", bike.getId());
        return bike;
    }

}
