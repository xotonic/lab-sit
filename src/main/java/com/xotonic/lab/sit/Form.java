package com.xotonic.lab.sit;

import com.xotonic.lab.sit.settings.SettingsController;
import com.xotonic.lab.sit.settings.SettingsModel;
import com.xotonic.lab.sit.settings.SettingsView;
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

public class Form extends JDialog implements KeyListener, SettingsView {

    static Logger log = LogManager.getLogger(Form.class.getName());


    private JPanel contentPane;
    private JPanel drawPanel;
    private JPanel propertiesPanel;
    private JButton sideBarStart;
    private JButton sideBarStop;
    private JCheckBox sideBarInfoToggle;
    private JRadioButton sideBarTimeShow;
    private JRadioButton sideBarTimeHide;
    private JButton toolbarStartStop;
    private JButton toolbarInfo;
    private JButton tollbarTime;
    private JPanel factoriesSettingsPanel;

    private DrawPanel drawer;
    private Habitat habitat = new SimpleHabitat();
    private TimedLuckyFactory carFactory = new CarFactory(habitat);
    private TimedLuckyFactory bikeFactory = new BikeFactory(habitat);
    private Painter painter;
    private SimulationTimer timer;
    private SettingsModel settingsModel;
    private SettingsController settingsController;
    private MenuView menuView;
    private ToolBarView toolBarView;
    private SideBarView sideBarView;

    public Form() {
        setContentPane(contentPane);
        setModal(true);
        addKeyListener(this);

        habitat.getPainters().add(painter);

        timer = new SimulationTimer();
        timer.setTarget(habitat);

        settingsModel = new SettingsModel();

        settingsController = new SettingsController();
        settingsController.setModel(settingsModel);

        menuView = new MenuView();
        menuView.setController(settingsController);
        settingsController.addView(menuView);
        setJMenuBar(menuView.getMenuBar());

        toolBarView = new ToolBarView(toolbarStartStop, toolbarInfo, tollbarTime);
        toolBarView.setController(settingsController);
        settingsController.addView(toolBarView);

        sideBarView = new SideBarView(
                sideBarStart,
                sideBarStop,
                sideBarInfoToggle,
                sideBarTimeShow,
                sideBarTimeHide
        );

        sideBarView.setController(settingsController);
        settingsController.addView(sideBarView);

        settingsController.addView(this);

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

    private void createUIComponents() {
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
}
