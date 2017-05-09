package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.SelectorType;
import com.google.gson.JsonObject;

import java.util.Map;

public class SelectorImpl implements Selector{
    private Map<String, String> parametersSQL;
    private JsonObject valueJSON;
    private SelectorType type;

    public Map<String, String> getParametersSQL() {
        return parametersSQL;
    }

    public void setParametersSQL(Map<String, String> parametersSQL) {
        this.parametersSQL = parametersSQL;
    }

    public JsonObject getValueJSON(Map<String, String> value) {
        return valueJSON;
    }

    public void setValueJSON(JsonObject valueJSON) {
        this.valueJSON = valueJSON;
    }

    public SelectorType getSelectorType() {
        return type;
    }

    public void setType(SelectorType type) {
        this.type = type;
    }
}
