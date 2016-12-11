package com.xotonic.lab.sit.vehicle;

/**
 * Изменяемый мир, доступен только нескольким объектам
 */
public class MutableWorld extends World {

    public void setAreaWidth(int value)
    {
        areaWidth = value;
    }

    public void setAreaHeight(int value)
    {
        areaHeight = value;
    }

    public void setTimeMillis(long time)
    {
        timeMillis = time;
    }
}
