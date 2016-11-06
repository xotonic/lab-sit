package com.xotonic.lab.sit.settings;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class SettingsController {
    Logger log = LogManager.getLogger(SettingsController.class.getName());

    private Collection<SettingsView> views = new ArrayList<>();
    private SettingsModel model;

    public void setModel(SettingsModel model) {
        log.debug("o/");

        this.model = model;

        updateFull();
    }

    private void updateFull() {
        log.debug("o/");

        updateShowInfo();
        updateShowTime();
        updateSimulationState();
    }

    private void updateSimulationState() {
        views.forEach(model.simulationState == SettingsModel.SimulationState.start ?
                SettingsView::OnSimulationStart : SettingsView::OnSimulationStop);
    }

    private void updateShowTime() {
        log.debug("o/");

        views.forEach(model.showTime ? SettingsView::OnShowTime : SettingsView::OnHideTime);
    }

    private void updateShowInfo() {
        log.debug("o/");

        views.forEach(model.showInfo ? SettingsView::OnShowInfo : SettingsView::OnHideInfo);
    }

    public void addView(SettingsView view) {
        log.debug("o/");

        views.add(view);
        updateFull();
    }

    public void setStart() {
        log.debug("o/");

        model.simulationState = SettingsModel.SimulationState.start;
        updateSimulationState();
    }

    public void setStop() {
        log.debug("o/");

        model.simulationState = SettingsModel.SimulationState.stop;
        updateSimulationState();
    }

    public void setShowTime(boolean show) {
        log.debug("o/ show = {}", show);

        model.showTime = show;
        updateShowTime();
    }

    public void setShowInfo(boolean show) {
        log.debug("o/ show = {}", show);

        model.showInfo = show;
        updateShowInfo();
    }

    public void setFactoryPeriod(SettingsModel.FactoryType type, float cooldown) {
        model.factoriesSettings.get(type).bornPeriod = cooldown;
    }

    public void setFactoryChance(SettingsModel.FactoryType type, float chance) {
        model.factoriesSettings.get(type).bornChance = chance;
    }

}
