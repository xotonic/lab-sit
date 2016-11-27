package com.xotonic.lab.sit.settings;


public enum FactoryType {
    car(new FactoryModel(100, 0.2f)),
    bike(new FactoryModel(200, 0.2f));

    private FactoryModel defaultModel;

    FactoryType(FactoryModel defaultModel) {
        this.defaultModel = defaultModel;
    }

    public FactoryModel getDefaultModel() {
        return defaultModel;
    }
}
