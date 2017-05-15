package com.dreamteam.datavisualizator.models.impl;

import com.google.gson.JsonObject;

import java.util.Map;

public class SelectorActiveJobs extends AbstractSelector {
    private int hourCount;

    public void setHourCount(int hourCount) {
        this.hourCount = hourCount;
    }

    public int getHourCount(){
        return this.hourCount;
    }

    public JsonObject getValueJSON() {
        return null;
    }
}
