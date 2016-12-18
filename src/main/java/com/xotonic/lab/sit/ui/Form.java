package com.xotonic.lab.sit.ui;

import com.xotonic.lab.sit.settings.factory.FactorySettingsController;
import com.xotonic.lab.sit.settings.factory.FactorySettingsModel;
import com.xotonic.lab.sit.settings.factory.FactoryType;
import com.xotonic.lab.sit.settings.settings.SettingsController;
import com.xotonic.lab.sit.settings.settings.SettingsModel;
import com.xotonic.lab.sit.settings.settings.SettingsView;
import com.xotonic.lab.sit.vehicle.Habitat;
import com.xotonic.lab.sit.vehicle.MutableWorld;
import com.xotonic.lab.sit.vehicle.SimpleHabitat;
import com.xotonic.lab.sit.vehicle.TimedLuckyFactory;
import com.xotonic.lab.sit.vehicle.bike.BikeFactory;
import com.xotonic.lab.sit.vehicle.car.CarFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Главная форма */

 /*
    TODO
    1)  --- создать абстрактный класс AI, описывающий «интеллектуальное поведение» объектов по варианту.
        --- Класс должен быть выполнен в виде отдельного потока и работать с коллекцией объектов;
    2)  --- реализовать класс AI для каждого из видов объекта, включив в него поведение,
        --- описанное в индивидуальном задании по варианту;
    3)  --- синхронизовать работу потоков расчета интеллекта объектов с их рисованием.
        --- Рисование должно остаться в основном потоке.
        --- Синхронизация осуществляется через передачу данных в основной поток;
    4)  добавить в панель управления кнопки для остановки и возобновления работы интеллекта
        каждого вида объектов. Реализовать через засыпание/пробуждение потоков;
    5)  добавить в панель управления выпадающие списки для выставления приоритетов каждого из потоков.
    6)  реализовать сохранение объектов в файл.
    7)  реализовать сохранение и загрузку настроек параметров программы в локальный файл.

    Вариант 8
    1. Автомобили двигаются по оси X от одного края области симуляции до другого со скоростью V.
    2. Мотоциклы двигаются по оси Y от одного края области симуляции до другого со скоростью V.
 */

public class Form extends JFrame
        implements KeyListener,
        SettingsView<JPanel, SettingsController>
{

    private static Logger log = LogManager.getLogger(Form.class.getName());

    /**
     * Главная панель
     */
    private JPanel contentPane;
    /**
     * Панель для отрисовки
     */
    private JPanel drawPanel;

    /** Параметры мира */
    private MutableWorld world;
    /** Окружение */
    private Habitat habitat;
    /** Фабрика машин */
    private TimedLuckyFactory carFactory;
    /** Фабрика мотоциклов */
    private TimedLuckyFactory bikeFactory;

    /* Вспомогательные классы */
    private Canvas canvas;
    private SimulationHandler simulation;
    private StatisticDialog statisticDialog;

    /* - Система MVC - */

    /* Модели */
    private SettingsModel settingsModel;
    private SettingsController settingsController;

    /* MVC для настроек фабрик */
    private FactorySettingsModel factoriesModel;
    private FactorySettingsController factoriesController;

    /* Вьюшки */
    /** Меню */
    private MenuView menuView;
    /** Панель инструментов */
    private ToolBarView toolBarView;
    /** Боковая панель */
    private SideBarView sideBarView;
    /** Панель настройки фабрики машин */
    private FactoryOptionsView carsSettingsView;
    /** Панель настройки фабрики байков */
    private FactoryOptionsView bikesSettingsView;

    public Form() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(this);

        world = new MutableWorld();
        world.setAreaHeight(600);
        world.setAreaWidth(800);
        world.setTimeMillis(0);

        habitat = new SimpleHabitat();

        carFactory = new CarFactory(habitat);
        bikeFactory = new BikeFactory(habitat);

        createDrawPanel();

        simulation = new SimulationHandler();
        simulation.setHabitat(habitat);
        simulation.setWorld(world);
        simulation.setCanvas(canvas);


        statisticDialog = new StatisticDialog(this);
        statisticDialog.setOnConfirmListener(() -> simulation.reset()); // controller.setStop();
        statisticDialog.setOnCancelListener(() -> settingsController.setStart());
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

        SwingUtilities.invokeLater( () ->
        {
            final Form dialog = new Form();
            dialog.pack();
            dialog.setVisible(true);
        });
        log.debug("Program exit");
    }

    /** Установка цветовой схемы */
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

    /** Создать панель отрисовки */
    private void createDrawPanel() {
        Canvas panel = new Canvas();

        drawPanel = panel;
        canvas = panel;

        panel.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                habitat.setWorldWidth(canvas.getWidth());
                habitat.setWorldHeight(canvas.getHeight());
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
        log.debug("KEY %s", e.getKeyChar());
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
        simulation.start();
    }

    private void toggleShowTime() {
        canvas.setShowTime(!canvas.isShowTime());
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
        Statistic stats =  getStatistic();

        showCanvasStatistic(stats);

        if (settingsModel.showInfo)
            showStatisticDialog(stats);
        else simulation.reset();
    }

    private Statistic getStatistic() {
        log.debug("o/");
        Statistic statistic = new Statistic();
        statistic.setTotalCarsCreated(carFactory.getTotalCreated());
        statistic.setTotalBikesCreated(bikeFactory.getTotalCreated());
        statistic.setTotalTime(simulation.getSimulationTime());
        return statistic;
    }

    private void showCanvasStatistic(Statistic statistic) {
        canvas.setStatistic(statistic);
    }

    private void showStatisticDialog(Statistic statistic) {
        statisticDialog.setStatistic(statistic);
        statisticDialog.show();
    }

    @Override
    public void OnShowInfo() {

    }

    @Override
    public void OnHideInfo() {

    }

    @Override
    public void OnShowTime() {
        canvas.setShowTime(true);

    }

    @Override
    public void OnHideTime() {
        canvas.setShowTime(false);

    }


   /** Создать интерфейс */
 @Override
    public void initializeUI() {

     int spriteMaxWidth = 155;
     int spriteMaxHeight = 81;
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setInheritsPopupMenu(false);
     contentPane.setPreferredSize(new Dimension(
             world.getAreaWidth(),
             world.getAreaHeight()));
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

}
