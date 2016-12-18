package com.xotonic.lab.sit;

/**
 * Created by xotonic on 18.12.16.
 */
public class MyMath {
    public static float clamp(float min, float value, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float reflect(float min, float value, float max, float step) {
        if (value + step >= max) {
            return max - Math.abs(step - Math.abs(max - value));
        }
        if (value + step <= min) {
            return min + Math.abs(step - Math.abs(value - min));
        }
        return value + step;
    }
}
