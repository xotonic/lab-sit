package com.xotonic.lab.sit.settings;


import com.xotonic.lab.sit.settings.factory.FactoryModel;
import com.xotonic.lab.sit.settings.factory.FactoryType;

import java.util.HashMap;
import java.util.Map;

/**
 * Вся сохраняемая информация о настройках программы
 */
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
         factoriesSettings.put(FactoryType.bike, new FactoryModel(100, 0.5f));
         factoriesSettings.put(FactoryType.car, new FactoryModel(100, 0.5f));
    }

    public enum SimulationState {start, stop}
}
