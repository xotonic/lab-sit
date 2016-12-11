package com.xotonic.lab.sit.settings.factory;


import com.xotonic.lab.sit.settings.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class FactorySettingsController
        implements Controller<FactorySettingsModel, FactorySettingsView>
{
    private static Logger log = LogManager.getLogger(FactorySettingsController.class.getName());


    private FactorySettingsModel model;
    private Collection<FactorySettingsView> views = new ArrayList<>();

    @Override
    public void setModel(FactorySettingsModel model) {
        this.model = model;
        updateFullDefault();
    }

    private void updateFullDefault()
    {
        views.forEach(v -> {
            v.OnBornPeriodChanged(
                    v.getFactoryType().getDefaultModel().bornPeriod);
            v.OnBornChanceChanged(
                    v.getFactoryType().getDefaultModel().bornChance);
        });
    }

    @Override
    public void addView(FactorySettingsView view) {
        views.add(view);
        updateFullDefault();
    }

    public void setBornChance(FactorySettingsView sender, float value)
    {
        log.debug("sender: {}; type: {} value : {}", sender.hashCode(), sender.getFactoryType().name(), value);

        model.factoriesSettings.get(sender.getFactoryType()).bornChance = value;
        updateBornChance(sender);
    }

    public void setBornPeriod(FactorySettingsView sender, int value)
    {
        log.debug("sender: {}; type: {} value : {}", sender.hashCode(), sender.getFactoryType().name(), value);
        model.factoriesSettings.get(sender.getFactoryType()).bornPeriod = value;
        updateBornPeriod(sender);
    }


    private void updateBornPeriod(FactorySettingsView sender) {
            views.stream()
                    .filter(v -> v != sender & v.getFactoryType() == sender.getFactoryType())
                    .forEach(v -> v.OnBornPeriodChanged(
                            model.factoriesSettings.get(v.getFactoryType()).bornPeriod));

    }

    private void updateBornChance(FactorySettingsView sender) {
            views.stream()
                    .filter(v -> v != sender & v.getFactoryType() == sender.getFactoryType())
                    .forEach(v -> v.OnBornChanceChanged(
                            model.factoriesSettings.get(v.getFactoryType()).bornChance));

    }



}
