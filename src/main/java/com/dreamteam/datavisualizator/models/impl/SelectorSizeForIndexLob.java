package com.dreamteam.datavisualizator.models.impl;

import com.google.gson.JsonObject;

import java.util.Map;

public class SelectorSizeForIndexLob extends AbstractSelector {
    private String segment;

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getSegment(){
        return this.segment;
    }
}
