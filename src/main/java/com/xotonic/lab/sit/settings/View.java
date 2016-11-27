package com.xotonic.lab.sit.settings;


public interface View<ControllerType extends Controller> {

    void setController(ControllerType controller);
}
