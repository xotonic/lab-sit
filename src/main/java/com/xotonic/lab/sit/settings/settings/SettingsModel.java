package com.xotonic.lab.sit.settings.settings;


import com.xotonic.lab.sit.settings.Model;

public class SettingsModel implements Model {

    public SimulationState simulationState;
    public boolean showInfo;
    public boolean showTime;

    public int bikeAIThreadPriority;
    public int carAIThreadPriority;

    public boolean isCarAIToggled;
    public boolean isBikeAIToggled;

    enum SimulationState {start, stop}
}
