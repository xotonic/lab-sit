package com.xotonic.lab.sit.settings.factory;


public enum FactoryType {
    car(new FactoryModel(100, 1.0f)),
    bike(new FactoryModel(100, 1.0f));

    private FactoryModel defaultModel;

    FactoryType(FactoryModel defaultModel) {
        this.defaultModel = defaultModel;
    }

    public FactoryModel getDefaultModel() {
        return defaultModel;
    }
}
