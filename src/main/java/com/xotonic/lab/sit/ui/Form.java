package com.xotonic.lab.sit.ui;

import com.xotonic.lab.sit.settings.*;
import com.xotonic.lab.sit.vehicle.*;
import com.xotonic.lab.sit.vehicle.Painter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Form extends JDialog
        implements KeyListener,
                   SettingsView<JPanel, SettingsController>
{

    private static Logger log = LogManager.getLogger(Form.class.getName());


    private JPanel contentPane;
    private JPanel drawPanel;

    private Habitat habitat = new SimpleHabitat();
    private TimedLuckyFactory carFactory = new CarFactory(habitat);
    private TimedLuckyFactory bikeFactory = new BikeFactory(habitat);

    private Painter painter;
    private DrawPanel drawer;
    private SimulationTimer timer;


    private SettingsModel settingsModel;
    private SettingsController settingsController;

    private FactorySettingsModel factoriesModel;
    private FactorySettingsController factoriesController;

    private MenuView menuView;
    private ToolBarView toolBarView;
    private SideBarView sideBarView;
    private FactoryOptionsView carsSettingsView;
    private FactoryOptionsView bikesSettingsView;

    public Form() {
        setModal(true);
        addKeyListener(this);

        createDrawPanel();

        habitat.getPainters().add(painter);

        timer = new SimulationTimer();
        timer.setTarget(habitat);

        settingsModel = new SettingsModel();
        factoriesModel = new FactorySettingsModel();

        menuView = new MenuView();
        toolBarView = new ToolBarView();
        sideBarView = new SideBarView();
        carsSettingsView = new FactoryOptionsView(FactoryType.car);
        bikesSettingsView = new FactoryOptionsView(FactoryType.bike);

        log.debug("Initializing UI");
        menuView.initializeUI();
        toolBarView.initializeUI();
        sideBarView.initializeUI();
        carsSettingsView.initializeUI();
        bikesSettingsView.initializeUI();
        initializeUI();

        log.debug("Initializing settings system");
        settingsController = new SettingsController();
        settingsController.setModel(settingsModel);
        settingsController.addView(menuView);
        settingsController.addView(toolBarView);
        settingsController.addView(sideBarView);
        settingsController.addView(this);

        factoriesController = new FactorySettingsController();
        factoriesController.setModel(factoriesModel);
        factoriesController.addView(carsSettingsView);
        factoriesController.addView(bikesSettingsView);

        menuView.setController(settingsController);
        toolBarView.setController(settingsController);
        sideBarView.setController(settingsController);
        carsSettingsView.setController(factoriesController);
        bikesSettingsView.setController(factoriesController);

        FactoryManipulator carFactoryManipulator = new FactoryManipulator(carFactory, FactoryType.car);
        FactoryManipulator bikeFactoryManipulator = new FactoryManipulator(bikeFactory, FactoryType.bike);
        factoriesController.addView(carFactoryManipulator);
        factoriesController.addView(bikeFactoryManipulator);

    }

    public static void main(String[] args) {

        log.debug("Program start");
        setLookAndFeel();

        final Form dialog = new Form();
        dialog.pack();
        dialog.setVisible(true);
        log.debug("Program exit");
        System.exit(0);
    }

    private static void setLookAndFeel() {
        UIManager.put("nimbusBase", new Color(49, 247, 255));
        UIManager.put("nimbusBlueGrey", new Color(49, 51, 53));
        UIManager.put("control", new Color(49, 51, 53));
        UIManager.put("nimbusFocus", new Color(53, 255, 253));
        UIManager.put("text", new Color(189, 189, 189));
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void createDrawPanel() {
        DrawPanel panel = new DrawPanel();
        drawPanel = panel;
        painter = panel;
        drawer = panel;

        panel.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                habitat.setWorldWidth(drawer.getWidth());
                habitat.setWorldHeight(drawer.getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'b':
                startSimulation();
                break;
            case 'e': {
                stopSimulation();
            }
            break;
            case 't': {
                toggleShowTime();
            }
            break;
        }
    }

    private void startSimulation() {
        timer.start();
    }

    private void toggleShowTime() {
        drawer.setShowTime(!drawer.isShowTime());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void OnSimulationStart() {
        startSimulation();
    }

    @Override
    public void OnSimulationStop() {

        stopSimulation();
    }

    private void stopSimulation() {
        reportStatistic();
        timer.reset();
    }

    private void reportStatistic() {
        Statistic statistic = new Statistic();
        statistic.setTotalCarsCreated(carFactory.getTotalCreated());
        statistic.setTotalBikesCreated(bikeFactory.getTotalCreated());
        statistic.setTotalTime(timer.getSimulationTime());
        drawer.setStatistic(statistic);
    }

    @Override
    public void OnShowInfo() {

    }

    @Override
    public void OnHideInfo() {

    }

    @Override
    public void OnShowTime() {
        drawer.setShowTime(true);

    }

    @Override
    public void OnHideTime() {
        drawer.setShowTime(false);

    }


    @Override
    public void initializeUI() {


        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setInheritsPopupMenu(false);
        contentPane.setPreferredSize(new Dimension(800, 600));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.weightx = 1.0;
        gbc1.fill = GridBagConstraints.HORIZONTAL;

        contentPane.add(toolBarView.getRootComponent(), gbc1);

        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
         panel1.add(sideBarView.getRootComponent(), gbc);

        GridBagConstraints gbc0 = new GridBagConstraints();
        gbc0.gridx = 0;
        gbc0.gridy = 1;
        gbc0.weightx = 1.0;
        gbc0.weighty = 1.0;
        gbc0.fill = GridBagConstraints.BOTH;
        contentPane.add(panel1, gbc0);


        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.weightx = 1.0;
        gbc3.weighty = 1.0;
        gbc3.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc3);

        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 0;
        gbc4.gridy = 0;
        gbc4.weightx = 1.0;
        gbc4.weighty = 1.0;
        gbc4.fill = GridBagConstraints.BOTH;
        panel2.add(drawPanel, gbc4);

        setJMenuBar(menuView.getRootComponent());

        sideBarView.addFactorySettingsView(carsSettingsView);
        sideBarView.addFactorySettingsView(bikesSettingsView);

        setContentPane(contentPane);

    }

    public JPanel getRootComponent() {
        return contentPane;
    }


    @Override
    public void setController(SettingsController controller) {
        this.settingsController = controller;
    }

    private class FactoryManipulator
            implements FactorySettingsView<JComponent, FactorySettingsController>
    {
        private TimedLuckyFactory factory;
        private FactoryType ftype;

        private FactoryManipulator(TimedLuckyFactory factory, FactoryType ftype) {
            this.factory = factory;
            this.ftype = ftype;
        }

        @Override
        public void setController(FactorySettingsController controller) {
        }

        @Override
        public void initializeUI() {

        }

        @Override
        public JComponent getRootComponent() {
            return null;
        }

        @Override
        public void OnBornPeriodChanged(int bornPeriod) {
            factory.setCooldown(bornPeriod);
        }

        @Override
        public void OnBornChanceChanged(float bornChance) {
            factory.setCreateChance(bornChance);
        }

        @Override
        public FactoryType getFactoryType() {
            return ftype;
        }

        @Override
        public void setFactoryType(FactoryType type) {
        }
    }

}
