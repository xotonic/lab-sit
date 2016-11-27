package com.xotonic.lab.sit.settings;


import java.io.Serializable;

public class FactoryModel implements Serializable {

    int bornPeriod;
    float bornChance;

    public FactoryModel() {}
    public FactoryModel(int period, float chance)
    {
        bornPeriod = period;
        bornChance = chance;
    }
}
