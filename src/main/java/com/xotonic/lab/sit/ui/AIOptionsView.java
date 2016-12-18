package com.xotonic.lab.sit.ui;


import com.xotonic.lab.sit.settings.HasUI;
import com.xotonic.lab.sit.settings.settings.AISettingsController;
import com.xotonic.lab.sit.settings.settings.AISettingsView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class AIOptionsView implements AISettingsView<AISettingsController>, HasUI<JPanel> {

    private static Logger log = LogManager.getLogger(FactoryOptionsView.class.getName());

    private AISettingsController controller;
    private JPanel root;
    private JSpinner bikeSpinner;
    private JSpinner carSpinner;
    private JCheckBox bikeToggle;
    private JCheckBox carToggle;

    @Override
    public void setController(AISettingsController controller) {
        this.controller = controller;
    }

    @Override
    public void initializeUI() {
        root = new JPanel();
        root.setLayout(new GridBagLayout());
        root.setBorder(BorderFactory.createTitledBorder("AI options"));
        SpinnerModel carModel =
                new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerModel bikeModel =
                new SpinnerNumberModel(0, 0, 100, 1);
        bikeSpinner = new JSpinner(bikeModel);
        carSpinner = new JSpinner(carModel);

        bikeToggle = new JCheckBox("Enable");
        carToggle = new JCheckBox("Enable");

        JPanel carPanel = new JPanel(new GridBagLayout());
        carPanel.setBorder(BorderFactory.createTitledBorder("Car"));
        JPanel bikePanel = new JPanel(new GridBagLayout());
        bikePanel.setBorder(BorderFactory.createTitledBorder("Bike"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        root.add(carPanel);
        root.add(bikePanel);
        carPanel.add(carToggle, gbc);
        bikePanel.add(bikeToggle, gbc);
        carPanel.add(carSpinner, gbc);
        bikePanel.add(bikeSpinner, gbc);


        bikeSpinner.addChangeListener(e -> {
            SpinnerModel dateModel = bikeSpinner.getModel();
            if (dateModel instanceof SpinnerNumberModel) {
                controller.setBikeThreadPriority(((SpinnerNumberModel) dateModel).getNumber().intValue());
            }
        });

        carSpinner.addChangeListener(e -> {
            SpinnerModel dateModel = carSpinner.getModel();
            if (dateModel instanceof SpinnerNumberModel) {
                controller.setCarThreadPriority(((SpinnerNumberModel) dateModel).getNumber().intValue());
            }
        });

        bikeToggle.addActionListener(a ->
                controller.setBikeAIToggled(bikeToggle.isSelected()));

        carToggle.addActionListener(a ->
                controller.setCarAIToggled(carToggle.isSelected()));

    }

    @Override
    public JPanel getRootComponent() {
        return root;
    }

    @Override
    public void OnBikeThreadPriorityChanged(int priority) {
        //bikeSpinner.setValue(priority);
    }

    @Override
    public void OnCarThreadPriorityChanged(int priority) {
        //carSpinner.setValue(priority);
    }

    @Override
    public void OnCarAIToggled(boolean on) {
        //carToggle.setSelected(on);
    }

    @Override
    public void OnBikeAIToggled(boolean on) {
        //bikeToggle.setSelected(on);
    }
}
