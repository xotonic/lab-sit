package com.xotonic.lab.sit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public enum ResourceId {
    DEFAULT("default.png"),
    CAR("car.png"),
    BIKE("bike.png");

    static Logger log = LogManager.getLogger(ResourceId.class.getName());
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
        try {
            URL url = getClass().getClassLoader().getResource(resourcePath);
            BufferedImage image;
            log.debug("Loading resource '{}' with path '{}'", name(), resourcePath);
            image = ImageIO.read(new File((url.toURI().getPath())));
            return image;
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
    }
}
