package com.xotonic.lab.sit.vehicle.car;


import com.xotonic.lab.sit.MyMath;
import com.xotonic.lab.sit.vehicle.AI;
import org.apache.logging.log4j.LogManager;


public class CarAI implements AI {

    private static org.apache.logging.log4j.Logger log =
            LogManager.getLogger(CarAI.class.getName());

    private Output o = new Output();
    private float speed = 0.1f;

    @Override
    public Output think(Input input) {

        float x = input.me.getX();
        float signedSpeed = input.me.isMovingBack() ? speed : -speed;
        float step = input.timestep * signedSpeed;
        float nextX = MyMath.clamp(0.f, x + step, input.areaWidth);

        if (nextX >= input.areaWidth || nextX <= 0)
            input.me.setMovingBack(! input.me.isMovingBack());

        signedSpeed = input.me.isMovingBack() ? speed : -speed;
        step = input.timestep * signedSpeed;
        nextX = MyMath.clamp(0.f, x + step, input.areaWidth);

        o.x = nextX;
        o.y = input.me.getY();

        return o;
    }

}
