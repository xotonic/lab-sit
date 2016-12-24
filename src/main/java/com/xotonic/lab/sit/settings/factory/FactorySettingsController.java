package com.xotonic.lab.sit.settings.factory;


import com.xotonic.lab.sit.settings.Controller;
import com.xotonic.lab.sit.settings.TotalModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Контроллер настроек фабрики
 */
public class FactorySettingsController
        extends Controller<TotalModel, FactorySettingsView>
{
    private static Logger log = LogManager.getLogger(FactorySettingsController.class.getName());


    @Override
    public void setModel(TotalModel model) {
        super.setModel(model);
        updateFull();
    }

    private void updateFull() {
        views.forEach(v -> {
            v.OnBornPeriodChanged(
                    model.factoriesSettings.get(v.getFactoryType()).bornPeriod);
            v.OnBornChanceChanged(
                    model.factoriesSettings.get(v.getFactoryType()).bornChance);
        });
    }

    @Override
    public void addView(FactorySettingsView view) {
        super.addView(view);
        updateFull();
    }

    public void setBornChance(FactorySettingsView sender, float value) {
        log.debug("sender: {}; type: {} value : {}", sender.hashCode(), sender.getFactoryType().name(), value);

        model.factoriesSettings.get(sender.getFactoryType()).bornChance = value;
        updateBornChance(sender);
    }

    public void setBornPeriod(FactorySettingsView sender, int value) {
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
