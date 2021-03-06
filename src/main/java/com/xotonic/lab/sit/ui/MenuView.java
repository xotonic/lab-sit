package com.xotonic.lab.sit.ui;


import com.xotonic.lab.sit.settings.settings.SettingsController;
import com.xotonic.lab.sit.settings.settings.SettingsView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.File;

/** Меню */
public class MenuView implements SettingsView<JMenuBar, SettingsController> {

    private Logger log = LogManager.getLogger(MenuView.class.getName());

    private SettingsController controller;
    private JMenuBar menuBar;
    private JMenuItem startItem;
    private JMenuItem stopItem;
    private JCheckBoxMenuItem showInfoItem;
    private JRadioButtonMenuItem showTimeItem;
    private JRadioButtonMenuItem hideTimeItem;
    private JMenuItem openFileItem;
    private JMenuItem saveFileItem;
    private JMenuItem saveVehiclesItem;
    private JMenuItem loadVehiclesItem;
    private Form form;

    public MenuView(Form form) {
        this.form = form;
    }

    /**
     * Создать интерфейс
     */
 @Override
    public void initializeUI() {
        JMenu menuFile, menuSimulation;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menuFile = new JMenu("File");
        menuBar.add(menuFile);
     //JMenuItem nopeItem = new JMenuItem("Not implemented");
     //nopeItem.setEnabled(false);
     //menuFile.add(nopeItem);

     openFileItem = new JMenuItem("Open");
     saveFileItem = new JMenuItem("Save");
     saveVehiclesItem = new JMenuItem("Save objects");
     loadVehiclesItem = new JMenuItem("Open objects");
     menuFile.add(openFileItem);
     menuFile.add(saveFileItem);
     menuFile.add(saveVehiclesItem);
     menuFile.add(loadVehiclesItem);

        menuSimulation = new JMenu("Simulation");
        menuBar.add(menuSimulation);

        //a group of JMenuItems
        startItem = new JMenuItem("Start");
        startItem.setAccelerator(KeyStroke.getKeyStroke('b'));
        menuSimulation.add(startItem);
        stopItem = new JMenuItem("Stop");
        stopItem.setAccelerator(KeyStroke.getKeyStroke('e'));
        menuSimulation.add(stopItem);

        //a group of radio button menu items
        menuSimulation.addSeparator();
        ButtonGroup group = new ButtonGroup();

        showTimeItem = new JRadioButtonMenuItem("Show simulation time");
        group.add(showTimeItem);
        menuSimulation.add(showTimeItem);

        hideTimeItem = new JRadioButtonMenuItem("Hide simulation time");
        hideTimeItem.setAccelerator(KeyStroke.getKeyStroke('t'));
        group.add(hideTimeItem);
        menuSimulation.add(hideTimeItem);
        //a group of check box menu items
        menuSimulation.addSeparator();
        showInfoItem = new JCheckBoxMenuItem("Show information");
        menuSimulation.add(showInfoItem);

        setActionListeners();
    }

    private void setActionListeners() {
        startItem.addActionListener(a -> controller.setStart());
        stopItem.addActionListener(a -> controller.setStop());
        showInfoItem.addActionListener(a -> controller.setShowInfo(showInfoItem.getState()));
        showTimeItem.addActionListener(a -> controller.setShowInfo(true));
        hideTimeItem.addActionListener(a -> controller.setShowTime(false));

        openFileItem.addActionListener(a -> {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                form.loadModelsFromFile(file);
            }
        });

        saveFileItem.addActionListener(a -> {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                form.saveModelsToFile(file);
            }
        });

        saveVehiclesItem.addActionListener(a -> {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                form.saveObjectsToFile(file);
            }
        });
        loadVehiclesItem.addActionListener( a -> {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                form.loadObjectsFromFile(file);
            }
        });
    }



    @Override
    public void OnSimulationStart() {
        log.debug("o/");
        startItem.setEnabled(false);
        stopItem.setEnabled(true);
    }

    @Override
    public void OnSimulationStop() {
        log.debug("o/");
        stopItem.setEnabled(false);
        startItem.setEnabled(true);

    }

    @Override
    public void OnShowInfo() {
        showInfoItem.setState(true);
    }

    @Override
    public void OnHideInfo() {
        showInfoItem.setState(false);
    }

    @Override
    public void OnShowTime() {
        showTimeItem.setSelected(true);

    }

    @Override
    public void OnHideTime() {
        hideTimeItem.setSelected(true);
    }

    @Override
    public JMenuBar getRootComponent() {
        return menuBar;
    }


    @Override
    public void setController(SettingsController controller) {
        this.controller = controller;
    }
}
