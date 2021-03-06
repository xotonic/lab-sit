package com.xotonic.lab.sit.ui;

import com.xotonic.lab.sit.settings.factory.FactorySettingsController;
import com.xotonic.lab.sit.settings.factory.FactorySettingsView;
import com.xotonic.lab.sit.settings.factory.FactoryType;
import com.xotonic.lab.sit.vehicle.TimedLuckyFactory;

import javax.swing.*;

/** Класс который слушает контроллер фабрик и управляет ей (фабрикой) */
class FactoryManipulator
        implements FactorySettingsView<JComponent, FactorySettingsController> {
    private TimedLuckyFactory factory;
    private FactoryType ftype;

    FactoryManipulator(TimedLuckyFactory factory, FactoryType ftype) {
        this.factory = factory;
        this.ftype = ftype;
    }

    @Override
    public void setController(FactorySettingsController controller) {
    }

   /** Создать интерфейс */
 @Override
    public void initializeUI() {

    }

    @Override
    public JComponent getRootComponent() {
        return null;
    }

    @Override
    public void OnBornPeriodChanged(int bornPeriod) {
        factory.setCooldown(bornPeriod);
    }

    @Override
    public void OnBornChanceChanged(float bornChance) {
        factory.setCreateChance(bornChance);
    }

    @Override
    public FactoryType getFactoryType() {
        return ftype;
    }

    @Override
    public void setFactoryType(FactoryType type) {
    }
}
