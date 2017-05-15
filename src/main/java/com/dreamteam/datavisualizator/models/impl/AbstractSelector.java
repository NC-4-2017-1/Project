package com.dreamteam.datavisualizator.models.impl;

import com.dreamteam.datavisualizator.models.Selector;
import com.google.gson.JsonObject;

public abstract class AbstractSelector implements Selector {
    private JsonObject selectorJSON;

    public void setSelectorJSON(JsonObject selectorJSON) {
        this.selectorJSON = selectorJSON;
    }

    public JsonObject getSelectorJSON(){
        return selectorJSON;
    }
}
