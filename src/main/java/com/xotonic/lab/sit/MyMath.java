package com.xotonic.lab.sit;

/**
 * Created by xotonic on 18.12.16.
 */
public class MyMath {
    public static float clamp(float min, float value, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static <T, V> boolean isInstanceOf(V o1) {
        try {
            T test = (T) o1;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
