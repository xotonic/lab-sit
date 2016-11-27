package com.xotonic.lab.sit.settings;


public interface Controller<ModelType extends Model, ViewType extends View> {
    void setModel(ModelType model);
    void addView(ViewType view);
}
