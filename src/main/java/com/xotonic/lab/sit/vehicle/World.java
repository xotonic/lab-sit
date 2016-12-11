package com.xotonic.lab.sit.vehicle;


/** Обьект, который будет доступен любой сущности в каждый момент времени
 *  но только для чтения
 * */
public class World {

    protected long timeMillis;
    protected int areaHeight;
    protected int areaWidth;

    public long getTimeMillis() {
        return timeMillis;
    }

    public int getAreaHeight() {
        return areaHeight;
    }

    public int getAreaWidth() {
        return areaWidth;
    }

}
