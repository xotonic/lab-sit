package com.xotonic.lab.sit.settings.settings;


import com.xotonic.lab.sit.settings.Controller;
import com.xotonic.lab.sit.settings.TotalModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Стандартный контроллер для всех настроек */
public class SettingsController extends Controller<TotalModel, SettingsView>
{

    Logger log = LogManager.getLogger(SettingsController.class.getName());


    @Override
    public void setModel(TotalModel model) {
        super.setModel(model);
        updateFull();
    }

    private void updateFull() {
        log.debug("o/");

        updateShowInfo();
        updateShowTime();
        updateSimulationState();
    }

    private void updateSimulationState() {
        views.forEach(model.simulationState == TotalModel.SimulationState.start ?
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
        super.addView(view);
        updateFull();
    }

    public void setStart() {
        log.debug("o/");

        model.simulationState = TotalModel.SimulationState.start;
        updateSimulationState();
    }

    public void setStop() {
        log.debug("o/");

        model.simulationState = TotalModel.SimulationState.stop;
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

}
