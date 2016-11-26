package com.xotonic.lab.sit.settings;

import javax.swing.*;

public interface HasUI<RootComponent extends JComponent> {
    void initializeUI();
    RootComponent getRootComponent();
}
