package com.xotonic.lab.sit.settings;




public class SettingsModel implements Model {

    SimulationState simulationState;
    boolean showInfo;
    boolean showTime;

    enum SimulationState {start, stop, pause}
}
