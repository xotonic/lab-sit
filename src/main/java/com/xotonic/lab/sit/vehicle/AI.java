package com.xotonic.lab.sit.vehicle;

import java.util.Collection;

/**
 * Базовый класс для всех обьектов с ИИ
 */
public interface AI {

    /** Главный метод, тут должна быть вся логика */
    Output think(Input input);

    /** Параметры, которые будут подаваться на вход */
    class Input
    {
        /** Шаг времени */
        public long timestep;
        /** Размеры площади, по которой можно перемещаться */
        public float areaHeight, areaWidth;

        public Vehicle me;
        /** Другие виклы, кроме данной */
        public Collection<Vehicle> otherVehicles;
    }

    /** Параметры, которые будут отдаваться на выход */
    class Output
    {
        /** Координаты */
        public float x, y;
        /** Едет ли назад */
        public boolean isMovingBack;
    }

}
