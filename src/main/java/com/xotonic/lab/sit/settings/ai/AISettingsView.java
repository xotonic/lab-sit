package com.xotonic.lab.sit.settings.ai;

import com.xotonic.lab.sit.settings.View;


public interface AISettingsView<SettingsControllerType extends AISettingsController>

        extends View<SettingsControllerType> {

    void OnBikeThreadPriorityChanged(int priority);

    void OnCarThreadPriorityChanged(int priority);

    void OnCarAIToggled(boolean on);

    void OnBikeAIToggled(boolean on);
}
