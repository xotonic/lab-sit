package com.xotonic.lab.sit.ui;


import com.xotonic.lab.sit.vehicle.Painter;
import com.xotonic.lab.sit.vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


class DrawPanel extends JPanel implements Painter {
    private Collection<Vehicle> vehicles;
    private long lastUpdatedTime = 0;
    private boolean started = false;
    private boolean stopped = false;
    private boolean isShowTime = true;
    private Statistic statistic;

    DrawPanel() {
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


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (vehicles != null)
            drawVehicles(g);

        if (isShowTime) {

            drawLinesTopLeft(g,
                    String.format("Time : %d", lastUpdatedTime),
                    started ? "Simulation start" : "Simulation stop"
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
        for (Vehicle v : vehicles) {

            BufferedImage img = v.getResourceId().getImage();
            g.drawImage(img, Math.round(v.getX()), Math.round(v.getY()), this);
        }
    }


    @Override
    public void start() {

        started = true;
        stopped = false;
    }

    @Override
    public void update(long timeMillis) {
        log.trace("DrawPanel update");
        lastUpdatedTime = timeMillis;
        repaint();
    }

    @Override
    public void stop() {
        started = false;
        stopped = true;
        repaint();
    }

    @Override
    public void onRepaint(Collection<Vehicle> vehicles) {
        if (this.vehicles == null) {
            this.vehicles = vehicles;
        }

    }

    private void drawLinesCenter(Graphics g, String... lines) {
        Color temp = g.getColor();
        Font font = new Font("Consolas", 1, 36);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
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

    private void drawLinesTopLeft(Graphics g, String... lines) {
        Color temp = g.getColor();
        Font font = new Font("Arial", 1, 12);
        g.setFont(font);
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
