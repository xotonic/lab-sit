package com.xotonic.lab.sit.ui;

import com.xotonic.lab.sit.network.Client;
import com.xotonic.lab.sit.settings.TotalModel;
import com.xotonic.lab.sit.settings.ai.AISettingsController;
import com.xotonic.lab.sit.settings.factory.FactorySettingsController;
import com.xotonic.lab.sit.settings.factory.FactoryType;
import com.xotonic.lab.sit.settings.settings.SettingsController;
import com.xotonic.lab.sit.settings.settings.SettingsView;
import com.xotonic.lab.sit.vehicle.MutableWorld;
import com.xotonic.lab.sit.vehicle.SimpleHabitat;
import com.xotonic.lab.sit.vehicle.TimedLuckyFactory;
import com.xotonic.lab.sit.vehicle.Vehicle;
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
import java.io.*;
import java.util.Collection;
import java.util.List;

/** Главная форма */

public class Form extends JFrame
        implements  KeyListener,
                    SettingsView<JPanel,
                    SettingsController>,
                    Client.ServerListener
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
    private SimpleHabitat habitat;
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
    private TotalModel totalModel;

    /* Контроллеры */
    private FactorySettingsController factoriesController;
    private AISettingsController aiSettingsController;
    private SettingsController settingsController;

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
    /**
     * Панель с настройками ИИ
     */
    private AIOptionsView aiOptionsView;
    private Client client;

    public Form() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(this);

        /*
         СОЗДАНИЕ И СВЯЗЫВАНИЕ ВСЕХ КОМПОНЕНТОВ В ОДНОМ МЕСТЕ
         */

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
        totalModel = new TotalModel();

        menuView = new MenuView(this);
        toolBarView = new ToolBarView();
        sideBarView = new SideBarView();
        carsSettingsView = new FactoryOptionsView(FactoryType.car);
        bikesSettingsView = new FactoryOptionsView(FactoryType.bike);
        aiOptionsView = new AIOptionsView();

        log.debug("Initializing UI");
        menuView.initializeUI();
        toolBarView.initializeUI();
        sideBarView.initializeUI();
        carsSettingsView.initializeUI();
        bikesSettingsView.initializeUI();
        aiOptionsView.initializeUI();
        initializeUI();

        log.debug("Initializing settings system");
        settingsController = new SettingsController();
        settingsController.setModel(totalModel);
        settingsController.addView(menuView);
        settingsController.addView(toolBarView);
        settingsController.addView(sideBarView);
        settingsController.addView(this);

        factoriesController = new FactorySettingsController();
        factoriesController.setModel(totalModel);
        factoriesController.addView(carsSettingsView);
        factoriesController.addView(bikesSettingsView);

        aiSettingsController = new AISettingsController();
        aiOptionsView.setController(aiSettingsController);
        aiSettingsController.setModel(totalModel);
        aiSettingsController.addView(aiOptionsView);
        aiSettingsController.addView(simulation);
        simulation.setController(aiSettingsController);

        menuView.setController(settingsController);
        toolBarView.setController(settingsController);
        sideBarView.setController(settingsController);
        carsSettingsView.setController(factoriesController);
        bikesSettingsView.setController(factoriesController);

        FactoryManipulator carFactoryManipulator = new FactoryManipulator(carFactory, FactoryType.car);
        FactoryManipulator bikeFactoryManipulator = new FactoryManipulator(bikeFactory, FactoryType.bike);
        factoriesController.addView(carFactoryManipulator);
        factoriesController.addView(bikeFactoryManipulator);

        connectToServer();
    }

     private void connectToServer() {
        try {
            client = new Client(habitat, "localhost", 8888, this);
            client.start();
            client.requestClientsList();
        } catch (IOException e)
        {
            log.error(e);
        }
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

        if (totalModel.showInfo)
            showStatisticDialog(stats);
        else simulation.reset();
    }

    /**
     * Собрать статистику
     */
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
        sideBarView.addAISettingsView(aiOptionsView);
        sideBarView.createClientsList( name -> client.swapObjects(name));

        setContentPane(contentPane);
    }

    public JPanel getRootComponent() {
        return contentPane;
    }


    @Override
    public void setController(SettingsController controller) {
        this.settingsController = controller;
    }

    /** Сохранить настройки в файл */
    public void saveModelsToFile(File f) {
        try {
            FileOutputStream saveFile = new FileOutputStream(f);
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(totalModel);
            save.close(); // This also closes saveFile.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Загрузить настройки из файла */
    public void loadModelsFromFile(File f) {
        try {
            FileInputStream saveFile = new FileInputStream(f);
            ObjectInputStream save = new ObjectInputStream(saveFile);
            totalModel = (TotalModel) save.readObject();
            save.close();

            aiSettingsController.setModel(totalModel);
            settingsController.setModel(totalModel);
            factoriesController.setModel(totalModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Сохранить объекты в файл
     */
    public void saveObjectsToFile(File f) {
        try {
            FileOutputStream saveFile = new FileOutputStream(f);
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(habitat.getVehicles());
            save.close(); // This also closes saveFile.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сохранить объекты в файл
     */
    public void loadObjectsFromFile(File f) {
        try {
            FileInputStream saveFile = new FileInputStream(f);
            ObjectInputStream save = new ObjectInputStream(saveFile);
            Collection<Vehicle> v = (Collection<Vehicle>) save.readObject();
            save.close();
            habitat.setVehicles(v);
            simulation.reloadVehicles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clientsListUpdated(List<String> clients) {
        sideBarView.updateList(clients.toArray(new String[]{}));
    }

    @Override
    public void vehiclesUpdated(Collection<Vehicle> v) {
        log.debug("Got {} vehicles", v.size());
        synchronized (habitat.getVehicles()) {
            habitat.setVehicles(v);
            simulation.reloadVehicles();
        }
    }
}
