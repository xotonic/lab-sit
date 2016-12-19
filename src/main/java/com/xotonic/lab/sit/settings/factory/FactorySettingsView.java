package com.xotonic.lab.sit.settings.factory;


import com.xotonic.lab.sit.settings.HasUI;
import com.xotonic.lab.sit.settings.View;

import javax.swing.*;

/**
 * Представление настроек фабрики
 */
public interface FactorySettingsView
        <RootComponent extends JComponent,
                SettingsControllerType extends FactorySettingsController>

        extends
        HasUI<RootComponent>,
        View<SettingsControllerType>
{
    void OnBornPeriodChanged(int bornPeriod);

    void OnBornChanceChanged(float bornChance);

    FactoryType getFactoryType();

    void setFactoryType(FactoryType type);
}
