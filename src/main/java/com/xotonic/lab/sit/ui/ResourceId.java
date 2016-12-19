package com.xotonic.lab.sit.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/** Загрузчик ресурсов */
public enum ResourceId {

    DEFAULT("default.png"),
    CAR("car.png"),
    CAR_BACK("car_back.png"),
    BIKE("bike_back.png"),
    BIKE_BACK("bike.png");

    private String resourcePath;
    private BufferedImage image;

    ResourceId(String resourcePath) {
        System.setProperty("sun.java2d.opengl", "true");
        this.resourcePath = resourcePath;
        image = loadResource(resourcePath);
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public BufferedImage getImage() {
        return image;
    }

    /**
     * Загрузить картинку из файла ресурсов
     */
    private BufferedImage loadResource(String resourcePath) {
        Logger log = LogManager.getLogger(ResourceId.class.getName());
        log.debug("Loading resource '{}' with path '{}'", name(), resourcePath);
        try {
            BufferedImage image;
            image = ImageIO.read(getClass().getResource(resourcePath));
            return toCompatibleImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
            return getFailedLoadingImage();
        } catch (Exception e) {
            log.error("Exception during loading resource", e);
            return getFailedLoadingImage();
        }
    }

    /** Получить аварийное изображение */
    private BufferedImage getFailedLoadingImage() {
        Logger log = LogManager.getLogger(ResourceId.class.getName());

        log.debug("o/");

        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.RED);
        g.drawString("fail " + name(), 5, 20);
        g.drawRect(1, 1,62, 62);
        image.flush();
        return image;
    }

    /** Оптимизация изображения для видеокарты */
    private BufferedImage toCompatibleImage(BufferedImage image) {
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

    /*
     * if image is already compatible and optimized for current system
     * settings, simply return it
     */
        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }


}
