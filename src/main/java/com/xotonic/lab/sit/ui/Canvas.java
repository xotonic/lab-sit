package com.xotonic.lab.sit.ui;


import com.xotonic.lab.sit.vehicle.Vehicle;
import com.xotonic.lab.sit.vehicle.World;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


/** Панель отрисовки */
class Canvas extends JPanel {
    Font centerFont = new Font("Consolas", 1, 36);
    Font cornerFont = new Font("Arial", 1, 12);
    private Collection<Vehicle> vehicles;
    private long lastUpdatedTime = 0;
    private boolean started = false;
    private boolean stopped = false;
    private boolean isShowTime = true;
    private Statistic statistic;

    Canvas() {
        super();
    }

    boolean isShowTime() {
        return isShowTime;
    }

    void setShowTime(boolean showTime) {
        isShowTime = showTime;
    }

    public void setStatistic(final Statistic statistic) {
        this.statistic = statistic;
    }


    /** Отрисовка  */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (started) {
            drawVehicles(g);
        }
        if (isShowTime) {

            drawLinesTopLeft(g,
                    String.format("Time : %d", lastUpdatedTime),
                    started ? "Simulation start" : "Simulation stop",
                    String.format("Current : %d vehicles", vehicles.size())
            );
        }


        if (stopped) {

            assert statistic != null;

            drawLinesCenter(g,
                    "Simulation stopped",
                    String.format("Total cars : %d", statistic.getTotalCarsCreated()),
                    String.format("Total bikes: %d", statistic.getTotalBikesCreated()),
                    String.format("Total time : %d", statistic.getTotalTime())
            );
        }

        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

    }

    private void drawVehicles(Graphics g) {

        synchronized (vehicles) {

            for (Vehicle v : vehicles) {

                Image img = v.getResourceId().getImage();
                g.drawImage(img, Math.round(v.getX()), Math.round(v.getY()), this);
            }
        }
    }


    public void start() {

        started = true;
        stopped = false;
    }

    public void update(World world) {
        lastUpdatedTime = world.getTimeMillis();
        repaint();
    }


    public void stop() {
        started = false;
        stopped = true;
        repaint();
    }

    public void setVehicles(Collection<Vehicle> vehicles) {
        if (this.vehicles == null) {
            this.vehicles = vehicles;
        }

    }

    /** Рисуем текст статистики */
    private void drawLinesCenter(Graphics g, String... lines) {
        Color temp = g.getColor();
        g.setFont(centerFont);
        FontMetrics metrics = g.getFontMetrics(centerFont);
        Optional<String> longest = Arrays.stream(lines).max((l1, l2) -> l1.length() > l2.length() ? 1 : -1);
        if (longest.isPresent()) {
            boolean isOdd = false;
            int currentX = getWidth() / 2 - metrics.stringWidth(longest.get()) / 2;
            int currentY = getHeight() / 2 - lines.length * metrics.getHeight() / 2;
            for (String s : lines) {
                g.setColor(isOdd ? new Color(135, 255, 52) : new Color(0, 167, 255));
                isOdd = !isOdd;
                g.drawString(s, currentX, currentY);
                currentY += metrics.getHeight();
            }
        }

        g.setColor(temp);
    }

    /** Рисуем текст в углу */
    private void drawLinesTopLeft(Graphics g, String... lines) {
        Color temp = g.getColor();

        g.setFont(cornerFont);
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int currentX = 10;
        int currentY = 20;
        for (String s : lines) {
            g.drawString(s, currentX, currentY);
            currentY += metrics.getHeight();
        }

        g.setColor(temp);
    }
}
