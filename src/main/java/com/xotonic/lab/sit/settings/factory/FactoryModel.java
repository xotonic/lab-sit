package com.xotonic.lab.sit.settings.factory;


import com.xotonic.lab.sit.settings.Model;


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
