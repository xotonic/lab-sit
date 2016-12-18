package com.xotonic.lab.sit.settings.settings;


import com.xotonic.lab.sit.settings.Model;

public class SettingsModel implements Model {

    public SimulationState simulationState;
    public boolean showInfo = false;
    public boolean showTime = true;

    public int bikeAIThreadPriority = 2;
    public int carAIThreadPriority = 2;

    public boolean isCarAIToggled = true;
    public boolean isBikeAIToggled = true;

    enum SimulationState {start, stop}
}
