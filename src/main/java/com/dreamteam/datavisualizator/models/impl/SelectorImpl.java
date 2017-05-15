package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Selector;
import com.google.gson.JsonObject;

import java.util.Map;

public class SelectorImpl implements Selector{
    private JsonObject valueJSON;

    public JsonObject getValueJSON() {
        return valueJSON;
    }

    public void setValueJSON(JsonObject valueJSON) {
        this.valueJSON = valueJSON;
    }

}
