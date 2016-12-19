package com.xotonic.lab.sit.settings;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Абстрактный контроллер.
 * Работает с одной моделью и множеством представлений
 */
public abstract class Controller<ModelType extends Model, ViewType extends View> {


    private static Logger log = LogManager.getLogger(Controller.class.getName());


    protected ModelType model;
    protected Collection<ViewType> views = new ArrayList<>();

    public void addView(ViewType view) {
        log.debug("o/");
        views.add(view);
    }

    public void setModel(ModelType model) {
        log.debug("o/");
        this.model = model;

    }
}
