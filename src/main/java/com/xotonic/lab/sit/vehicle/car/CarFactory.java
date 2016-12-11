package com.xotonic.lab.sit.vehicle.car;

import com.xotonic.lab.sit.vehicle.Habitat;
import com.xotonic.lab.sit.vehicle.TimedLuckyFactory;
import com.xotonic.lab.sit.vehicle.Vehicle;

import java.util.Random;


public class CarFactory extends TimedLuckyFactory {

    private Random r = new Random();

    public CarFactory(Habitat habitat) {
        super(habitat);
        cooldown = 100;
        setCreateChance(0.2f);
    }

    @Override
    public Vehicle create() {
        Car car = new Car(Car.class.getSimpleName() + "-" + getNextId());

        car.setX(r.nextFloat() * habitat.getWorldWidth());
        car.setY(r.nextFloat() * habitat.getWorldHeight());
        car.setMovingBack(r.nextBoolean());
        car.setAi(new CarAI());
        log.debug("Created car {}", car.getId());
        return car;
    }

}
