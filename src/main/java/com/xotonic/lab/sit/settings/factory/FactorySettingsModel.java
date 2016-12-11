package com.xotonic.lab.sit.settings.factory;


import com.xotonic.lab.sit.settings.Model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FactorySettingsModel implements Model {

    public Map<FactoryType, FactoryModel> factoriesSettings = new HashMap<>();

    {
        Arrays.stream(FactoryType.values())
                .forEach(type -> factoriesSettings.put(type, new FactoryModel()));
    }

}
