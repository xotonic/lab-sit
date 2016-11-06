package com.xotonic.lab.sit.settings;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface SettingsView {
    Logger log = LogManager.getLogger(SettingsView.class.getName());

    void OnSimulationStart();

    void OnSimulationStop();

    void OnShowInfo();

    void OnHideInfo();

    void OnShowTime();

    void OnHideTime();
}
