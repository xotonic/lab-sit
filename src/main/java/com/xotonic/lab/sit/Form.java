package com.xotonic.lab.sit;

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

public class Form extends JDialog implements KeyListener {

    static Logger log = LogManager.getLogger(Form.class.getName());

    Habitat habitat = new SimpleHabitat();
    TimedLuckyFactory carFactory = new CarFactory(habitat);
    TimedLuckyFactory bikeFactory = new BikeFactory(habitat);
    Painter painter;
    SimulationTimer timer;
    private JPanel contentPane;
    private JPanel drawPanel;
    private JPanel toolPanel;
    private DrawPanel drawer;

    public Form() {
        setContentPane(contentPane);
        setModal(true);
        addKeyListener(this);
        habitat.getPainters().add(painter);
        timer = new SimulationTimer();
        timer.setTarget(habitat);
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

    static void setLookAndFeel() {
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
                timer.start();
                break;
            case 'e': {
                drawer.setTotalCars(carFactory.getTotalCreated());
                drawer.setTotalBikes(bikeFactory.getTotalCreated());
                timer.reset();
            }
            break;
            case 't': {
                drawer.setShowTime(!drawer.isShowTime());
            }
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
