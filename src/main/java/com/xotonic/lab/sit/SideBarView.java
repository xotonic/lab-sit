package com.xotonic.lab.sit;

import com.xotonic.lab.sit.settings.SettingsController;
import com.xotonic.lab.sit.settings.SettingsView;

import javax.swing.*;
import java.awt.*;


public class SideBarView implements SettingsView {

    private SettingsController controller;

    private JButton sideBarStart;
    private JButton sideBarStop;
    private JCheckBox sideBarInfoToggle;
    private JRadioButton sideBarTimeShow;
    private JRadioButton sideBarTimeHide;
    private JPanel propertiesPanel;

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

    @Override
    public void initializeUI() {

        GridBagConstraints gbc;
        JPanel factoriesSettingsPanel;

        propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new GridBagLayout());

        propertiesPanel.setBorder(BorderFactory.createTitledBorder("Properties"));

        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        propertiesPanel.add(panel3, gbc);
        panel3.setBorder(BorderFactory.createTitledBorder("Simulation control"));

        sideBarStart = new JButton();
        sideBarStart.setText("Start");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel3.add(sideBarStart, gbc);
        sideBarStop = new JButton();
        sideBarStop.setText("Stop");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel3.add(sideBarStop, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        propertiesPanel.add(panel4, gbc);
        panel4.setBorder(BorderFactory.createTitledBorder("Simulation time"));

        sideBarTimeShow = new JRadioButton();
        sideBarTimeShow.setText("Show");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(sideBarTimeShow, gbc);

        sideBarTimeHide = new JRadioButton();
        sideBarTimeHide.setText("Hide");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(sideBarTimeHide, gbc);

        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        propertiesPanel.add(panel5, gbc);
        panel5.setBorder(BorderFactory.createTitledBorder("Information"));
        sideBarInfoToggle = new JCheckBox();
        sideBarInfoToggle.setText("Show");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel5.add(sideBarInfoToggle, gbc);

        factoriesSettingsPanel = new JPanel();
        factoriesSettingsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        propertiesPanel.add(factoriesSettingsPanel, gbc);

        ButtonGroup group = new ButtonGroup();
        group.add(sideBarTimeShow);
        group.add(sideBarTimeHide);

        setListeners();
    }

    @Override
    public JPanel getRootComponent() {
        return propertiesPanel;
    }
}
