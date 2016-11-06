package com.xotonic.lab.sit;

import com.xotonic.lab.sit.settings.SettingsController;
import com.xotonic.lab.sit.settings.SettingsView;

import javax.swing.*;


public class ToolBarView implements SettingsView {
    private SettingsController controller;

    private JButton startStopButton;
    private JButton infoButton;
    private JButton timeButton;

    private boolean started, isShowTime, isShowInfo;

    public ToolBarView(JButton startStopButton, JButton infoButton, JButton timeButton) {
        this.startStopButton = startStopButton;
        this.infoButton = infoButton;
        this.timeButton = timeButton;
        setListeners();
    }

    private void setListeners() {
        startStopButton.addActionListener(a -> {
            if (started) controller.setStop();
            else controller.setStart();
        });
        infoButton.addActionListener(a -> {
            controller.setShowInfo(!isShowInfo);
        });
        timeButton.addActionListener(a -> {
            controller.setShowTime(!isShowTime);
        });

    }

    public void setController(SettingsController c) {
        controller = c;
    }

    @Override
    public void OnSimulationStart() {
        startStopButton.setText("Stop");
        started = true;
    }

    @Override
    public void OnSimulationStop() {
        startStopButton.setText("Start");
        started = false;

    }

    @Override
    public void OnShowInfo() {
        infoButton.setText("Hide info");
        isShowInfo = true;
    }

    @Override
    public void OnHideInfo() {
        infoButton.setText("Show info");
        isShowInfo = false;

    }

    @Override
    public void OnShowTime() {
        timeButton.setText("Hide time");
        isShowTime = true;
    }

    @Override
    public void OnHideTime() {
        timeButton.setText("Show time");
        isShowTime = false;
    }
}
