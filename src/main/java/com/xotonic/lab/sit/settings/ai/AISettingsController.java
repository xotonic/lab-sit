package com.xotonic.lab.sit.settings.ai;

import com.xotonic.lab.sit.settings.Controller;
import com.xotonic.lab.sit.settings.TotalModel;

/**
 * Абстрактный контроллер настроек ИИ
 */
public class AISettingsController
        extends Controller<TotalModel, AISettingsView> {

    private void updateAll() {
        setBikeAIToggled(model.isBikeAIToggled);
        setBikeThreadPriority(model.bikeAIThreadPriority);
        setCarAIToggled(model.isCarAIToggled);
        setCarThreadPriority(model.carAIThreadPriority);
    }

    @Override
    public void addView(AISettingsView view) {
        super.addView(view);
        updateAll();
    }

    @Override
    public void setModel(TotalModel model) {
        super.setModel(model);
        updateAll();
    }

    public void setBikeThreadPriority(int priority) {
        model.bikeAIThreadPriority = priority;
        views.forEach(v -> v.OnBikeThreadPriorityChanged(model.bikeAIThreadPriority));
    }

    public void setCarThreadPriority(int priority) {
        model.carAIThreadPriority = priority;
        views.forEach(v -> v.OnCarThreadPriorityChanged(priority));
    }

    public void setBikeAIToggled(boolean on) {
        model.isBikeAIToggled = on;
        views.forEach(v -> v.OnBikeAIToggled(on));
    }

    public void setCarAIToggled(boolean on) {
        model.isCarAIToggled = on;
        views.forEach(v -> v.OnCarAIToggled(on));
    }
}
