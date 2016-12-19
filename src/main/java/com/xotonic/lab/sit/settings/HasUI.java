package com.xotonic.lab.sit.settings;

import javax.swing.*;

/**
 * Обьект, который имеет интерфейс,
 * а значит у него должен быть корневой элемент
 */
public interface HasUI<RootComponent extends JComponent> {
    /** Инициализация интерфейса. Навешивание обработчиков событий */
    void initializeUI();

    RootComponent getRootComponent();
}
