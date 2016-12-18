package com.xotonic.lab.sit.settings;


import com.xotonic.lab.sit.settings.factory.FactoryModel;
import com.xotonic.lab.sit.settings.factory.FactoryType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TotalModel implements Model {

    public SimulationState simulationState;
    public boolean showInfo = false;
    public boolean showTime = true;

    public int bikeAIThreadPriority = 2;
    public int carAIThreadPriority = 2;

    public boolean isCarAIToggled = true;
    public boolean isBikeAIToggled = true;
    public Map<FactoryType, FactoryModel> factoriesSettings = new HashMap<>();

    {
        Arrays.stream(FactoryType.values())
                .forEach(type -> factoriesSettings.put(type, new FactoryModel()));
    }

    public enum SimulationState {start, stop}
}
