package com.xotonic.lab.sit.ui;

import com.xotonic.lab.sit.settings.settings.SettingsController;
import com.xotonic.lab.sit.settings.settings.SettingsView;

import javax.swing.*;

/** Панель инструментов */
public class ToolBarView implements SettingsView<JToolBar, SettingsController> {
    private SettingsController controller;

    private JButton toolbarStartStop;
    private JButton toolbarInfo;
    private JButton toolbarTime;

    private boolean started, isShowTime, isShowInfo;
    private JToolBar toolBar;

    private void setListeners() {
        toolbarStartStop.addActionListener(a -> {
            if (started) controller.setStop();
            else controller.setStart();
        });
        toolbarInfo.addActionListener(a -> {
            controller.setShowInfo(!isShowInfo);
        });
        toolbarTime.addActionListener(a -> {
            controller.setShowTime(!isShowTime);
        });

    }

    @Override
    public void setController(SettingsController c) {
        controller = c;
    }

    @Override
    public void OnSimulationStart() {
        toolbarStartStop.setText("Stop");
        started = true;
    }

    @Override
    public void OnSimulationStop() {
        toolbarStartStop.setText("Start");
        started = false;

    }

    @Override
    public void OnShowInfo() {
        toolbarInfo.setText("Hide info");
        isShowInfo = true;
    }

    @Override
    public void OnHideInfo() {
        toolbarInfo.setText("Show info");
        isShowInfo = false;

    }

    @Override
    public void OnShowTime() {
        toolbarTime.setText("Hide time");
        isShowTime = true;
    }

    @Override
    public void OnHideTime() {
        toolbarTime.setText("Show time");
        isShowTime = false;
    }

   /** Создать интерфейс */
 @Override
    public void initializeUI() {
        toolBar = new JToolBar();
        toolbarStartStop = new JButton();
        toolbarStartStop.setText("Start");
        toolBar.add(toolbarStartStop);
        toolbarInfo = new JButton();
        toolbarInfo.setText("Info");
        toolBar.add(toolbarInfo);
        toolbarTime = new JButton();
        toolbarTime.setText("Time");
        toolBar.add(toolbarTime);

        setListeners();
    }

    @Override
    public JToolBar getRootComponent() {
        return toolBar;
    }
}
