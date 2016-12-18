package com.xotonic.lab.sit.vehicle.bike;


import com.xotonic.lab.sit.MyMath;
import com.xotonic.lab.sit.vehicle.AI;
import com.xotonic.lab.sit.vehicle.car.CarAI;
import org.apache.logging.log4j.LogManager;

public class BikeAI implements AI {

    private static org.apache.logging.log4j.Logger log =
            LogManager.getLogger(CarAI.class.getName());
    private Output o = new Output();
    private float speed = 0.1f;

    @Override
    public Output think(Input input) {

        float y = input.me.getY();
        float signedSpeed = input.me.isMovingBack() ? speed : -speed;
        float step = input.timestep * signedSpeed;
        float nextY = y + step;
        if (nextY >= input.areaHeight || nextY <= 0) {
            input.me.setMovingBack(!input.me.isMovingBack());
        }
        signedSpeed = input.me.isMovingBack() ? speed : -speed;
        step = input.timestep * signedSpeed;
        nextY = MyMath.reflect(0.f, y, input.areaHeight, step);

        o.y = nextY;
        o.x = input.me.getX();

        return o;
    }
}
