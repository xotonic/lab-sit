package com.xotonic.lab.sit.settings;

/**
 * Абстрактное представление
 * Работает только с контроллером, не имеет доступ к модели
 */
public interface View<ControllerType extends Controller> {

    void setController(ControllerType controller);
}
