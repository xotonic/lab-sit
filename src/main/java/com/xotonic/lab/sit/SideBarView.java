package com.xotonic.lab.sit;

import com.xotonic.lab.sit.settings.SettingsController;
import com.xotonic.lab.sit.settings.SettingsView;

import javax.swing.*;


public class SideBarView implements SettingsView {

    private SettingsController controller;

    private JButton sideBarStart;
    private JButton sideBarStop;
    private JCheckBox sideBarInfoToggle;
    private JRadioButton sideBarTimeShow;
    private JRadioButton sideBarTimeHide;

    public SideBarView(
            JButton sideBarStart,
            JButton sideBarStop,
            JCheckBox sideBarInfoToggle,
            JRadioButton sideBarTimeShow,
            JRadioButton sideBarTimeHide

            // TODO
        /*,
        JPanel factoriesSettingsContainer,
        JPanel factoryItemPrototype*/
    ) {
        this.sideBarStart = sideBarStart;
        this.sideBarStop = sideBarStop;
        this.sideBarInfoToggle = sideBarInfoToggle;
        this.sideBarTimeShow = sideBarTimeShow;
        this.sideBarTimeHide = sideBarTimeHide;

        ButtonGroup group = new ButtonGroup();
        group.add(sideBarTimeShow);
        group.add(sideBarTimeHide);

        setListeners();
    }

    private void setListeners() {
        sideBarStart.addActionListener(a -> controller.setStart());
        sideBarStop.addActionListener(a -> controller.setStop());
        sideBarInfoToggle.addActionListener(a ->
                controller.setShowInfo(sideBarInfoToggle.isSelected()));
        sideBarTimeShow.addActionListener(a -> controller.setShowTime(true));
        sideBarTimeHide.addActionListener(a -> controller.setShowTime(false));
    }

    @Override
    public void OnSimulationStart() {
        sideBarStart.setEnabled(false);
        sideBarStop.setEnabled(true);
    }

    @Override
    public void OnSimulationStop() {
        sideBarStop.setEnabled(false);
        sideBarStart.setEnabled(true);
    }

    @Override
    public void OnShowInfo() {
        sideBarInfoToggle.setSelected(true);
    }

    @Override
    public void OnHideInfo() {
        sideBarInfoToggle.setSelected(false);

    }

    @Override
    public void OnShowTime() {
        sideBarTimeShow.setSelected(true);
    }

    @Override
    public void OnHideTime() {
        sideBarTimeHide.setSelected(true);

    }

    public void setController(SettingsController controller) {
        this.controller = controller;
    }
}
