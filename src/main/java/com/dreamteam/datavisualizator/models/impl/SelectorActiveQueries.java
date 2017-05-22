package com.dreamteam.datavisualizator.models.impl;

import com.google.gson.JsonObject;

import java.util.Map;

public class SelectorActiveQueries  extends AbstractSelector {
    private int top;

    public void setTop(int top) {
        this.top = top;
    }

    public int getTop(){
        return this.top;
    }
}