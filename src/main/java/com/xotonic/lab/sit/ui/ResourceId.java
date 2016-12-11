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
    BIKE("bike.png");

    private String resourcePath;
    private BufferedImage image;

    ResourceId(String resourcePath) {
        this.resourcePath = resourcePath;
        image = loadResource(resourcePath);
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public BufferedImage getImage() {
        return image;
    }

    private BufferedImage loadResource(String resourcePath) {
        Logger log = LogManager.getLogger(ResourceId.class.getName());
        log.debug("Loading resource '{}' with path '{}'", name(), resourcePath);
        try {
            BufferedImage image;
            image = ImageIO.read(getClass().getResource(resourcePath));
            return image;
        } catch (IOException ex) {
            ex.printStackTrace();
            return getFailedLoadingImage();
        }
        catch (Exception e)
        {
            log.error("Exception during loading resource", e);
            return getFailedLoadingImage();
        }
    }


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


}
