package com.xotonic.lab.sit.ui;


import com.xotonic.lab.sit.settings.FactorySettingsController;
import com.xotonic.lab.sit.settings.FactorySettingsView;
import com.xotonic.lab.sit.settings.FactoryType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FactoryOptionsView implements FactorySettingsView<JPanel, FactorySettingsController> {

    private static final Map<FactoryType, String> localizedFactoryNames = new HashMap<>();
    private static Logger log = LogManager.getLogger(FactoryOptionsView.class.getName());

    static {
        localizedFactoryNames.put(FactoryType.car, "Cars options");
        localizedFactoryNames.put(FactoryType.bike, "Bike options");
    }

    private FactorySettingsController controller;
    private FactoryType factoryType;
    private JPanel root;
    private JTextField bornPeriodField;
    private JTextField bornChanceField;

    public FactoryOptionsView(FactoryType type)
    {
        setFactoryType(type);
    }

    private static void success(JTextField bornPeriodField) {
        bornPeriodField.setForeground(Color.BLACK);
        bornPeriodField.setBackground(Color.GREEN);
    }

    private static void fail(JTextField bornChanceField) {
        bornChanceField.setForeground(Color.BLACK);
        bornChanceField.setBackground(Color.RED);
    }

    private static void initial(JTextField bornPeriodField) {
        bornPeriodField.setForeground(Color.BLACK);
        bornPeriodField.setBackground(Color.WHITE);
    }

    @Override
    public void setController(FactorySettingsController controller) {
        this.controller = controller;
    }

    @Override
    public void initializeUI() {

        assert factoryType != null;

        root = new JPanel();
        root.setLayout(new GridBagLayout());

        bornChanceField = new JTextField();
        bornChanceField.setColumns(5);

        bornPeriodField = new JTextField();
        bornPeriodField.setColumns(5);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        root.add(bornPeriodField, gbc);
        gbc.gridy = 1;
        root.add(bornChanceField, gbc);

        root.setBorder(BorderFactory.createTitledBorder(localizedFactoryNames.get(factoryType)));


        bornChanceField.addActionListener(evt -> {
            log.debug("chance");
            updateBornChance();
        });
        bornPeriodField.addActionListener(evt -> {
            log.debug("period");
            updateBornPeriod();
        });

    }

    private void updateBornPeriod() {
        log.debug("o/");
        try {
            controller.setBornPeriod(
                    this,
                    Integer.parseInt(bornPeriodField.getText()));
            success(bornPeriodField);

        }
        catch (NumberFormatException e)
        {
            fail(bornPeriodField);
        }
    }

    private void updateBornChance() {
        log.debug("o/");
        try {
            controller.setBornChance(
                    this,
                    Float.parseFloat(bornChanceField.getText()));
            success(bornChanceField);

        }
        catch (NumberFormatException e)
        {
            fail(bornChanceField);
        }
    }

    @Override
    public JPanel getRootComponent() {
        return root;
    }

    @Override
    public void OnBornPeriodChanged(int bornPeriod) {
        bornPeriodField.setText(Integer.toString(bornPeriod));
        initial(bornPeriodField);

    }

    @Override
    public void OnBornChanceChanged(float bornChance) {
        bornChanceField.setText(Float.toString(bornChance));
        initial(bornChanceField);

    }

    @Override
    public FactoryType getFactoryType() {
        return factoryType;
    }

    @Override
    public void setFactoryType(FactoryType type)
    {
        this.factoryType = type;
    }


}
