package com.xotonic.lab.sit.settings.factory;


import com.xotonic.lab.sit.settings.Model;

/**
 * Информация о фабрике для сохранения в файл
 */
public class FactoryModel implements Model {

    int bornPeriod;
    float bornChance;

    public FactoryModel() {}
    public FactoryModel(int period, float chance)
    {
        bornPeriod = period;
        bornChance = chance;
    }
}
