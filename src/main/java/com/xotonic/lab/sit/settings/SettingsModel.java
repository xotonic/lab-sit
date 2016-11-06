package com.xotonic.lab.sit.settings;


import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class SettingsModel implements Serializable {
    public SimulationState simulationState;
    boolean showInfo;
    boolean showTime;
    Map<FactoryType, FactorySettings> factoriesSettings = new HashMap<>();

    {
        Arrays.stream(FactoryType.values())
                .forEach(type -> factoriesSettings.put(type, new FactorySettings()));
    }

    enum SimulationState {start, stop, pause}

    enum FactoryType {car, bike;}
}
