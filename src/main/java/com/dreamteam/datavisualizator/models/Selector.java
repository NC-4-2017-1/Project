package com.dreamteam.datavisualizator.models;


import com.google.gson.JsonObject;

import java.util.Map;

public interface Selector {
    Map<String, String> getParametersSQL();
    JsonObject getValueJSON(Map<String, String> value);
    SelectorType getSelectorType();
}
