package com.xotonic.lab.sit.ui;


import com.xotonic.lab.sit.settings.factory.FactorySettingsController;
import com.xotonic.lab.sit.settings.factory.FactorySettingsView;
import com.xotonic.lab.sit.settings.factory.FactoryType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Панель с настройками фабрики
 */
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

    private JComboBox<Float> bornChanceCombo;
    private Float[] chances = new Float[] {
            0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};

    public FactoryOptionsView(FactoryType type) {
        setFactoryType(type);
    }

    private void success(JTextField bornPeriodField) {
        bornPeriodField.setForeground(Color.BLACK);
        bornPeriodField.setBackground(Color.GREEN);
    }

    private void fail(JTextField bornChanceField) {
        bornChanceField.setForeground(Color.BLACK);
        bornChanceField.setBackground(Color.RED);

        JOptionPane.showMessageDialog(root, "Error in " + localizedFactoryNames.get(factoryType));
    }

    private void initial(JTextField bornPeriodField) {
        bornPeriodField.setForeground(Color.BLACK);
        bornPeriodField.setBackground(Color.WHITE);
    }

    @Override
    public void setController(FactorySettingsController controller) {
        this.controller = controller;
    }

    /** Создать интерфейс */
    @Override
    public void initializeUI() {

        assert factoryType != null;

        root = new JPanel();
        root.setLayout(new GridBagLayout());

        bornChanceCombo = new JComboBox<>();
        bornChanceCombo.setEditable(false);
        for (int chance = 0; chance < chances.length; chance++) {
            bornChanceCombo.addItem(chances[chance]);
        }

        bornPeriodField = new JTextField();
        bornPeriodField.setColumns(5);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        root.add(bornPeriodField, gbc);
        gbc.gridx = 1;
        root.add(bornChanceCombo, gbc);

        root.setBorder(BorderFactory.createTitledBorder(localizedFactoryNames.get(factoryType)));


        bornChanceCombo.addActionListener(evt -> {
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

        } catch (NumberFormatException e) {
            fail(bornPeriodField);
        }
    }

    private void updateBornChance() {
        log.debug("o/");

        Float selected = bornChanceCombo.getItemAt(bornChanceCombo.getSelectedIndex());
        if (selected!=null && controller!=null)
            controller.setBornChance(this, selected);
        else
            log.debug("Skip updating");

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
        int nextSelected = bornChanceCombo.getItemCount();

        List<Float> floats = Arrays.asList(chances);
        if (floats.contains(bornChance))
            bornChanceCombo.setSelectedIndex(floats.indexOf(bornChance));
        else {
            bornChanceCombo.addItem(bornChance);
            bornChanceCombo.setSelectedIndex(nextSelected);
        }
    }

    @Override
    public FactoryType getFactoryType() {
        return factoryType;
    }

    @Override
    public void setFactoryType(FactoryType type) {
        this.factoryType = type;
    }


}
