package com.xotonic.lab.sit.settings.settings;


import com.xotonic.lab.sit.settings.HasUI;
import com.xotonic.lab.sit.settings.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

/**
 * Абстракное представление основных настроек
 */
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
