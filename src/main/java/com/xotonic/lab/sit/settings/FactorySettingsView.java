package com.xotonic.lab.sit.settings;


import javax.swing.*;

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
