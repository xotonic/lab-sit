package com.xotonic.lab.sit.settings;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public interface SettingsView<RootComponent extends JComponent,
                              SettingsControllerType extends SettingsController>

        extends HasUI<RootComponent>,
                View<SettingsControllerType>
{

    Logger log = LogManager.getLogger(SettingsView.class.getName());

    void OnSimulationStart();
    void OnSimulationStop();

    void OnShowInfo();
    void OnHideInfo();

    void OnShowTime();
    void OnHideTime();
}
