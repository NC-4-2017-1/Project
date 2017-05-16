package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.google.gson.JsonObject;

public class GraphicHMImpl implements Graphic{
    private JsonObject graphicJSON;
    private int hourCount;

    public void setGraphicJSON(JsonObject graphicJSON) {
        this.graphicJSON = graphicJSON;
    }

    public JsonObject getGraphicJSON(){
        return graphicJSON;
    }

    public void setHourCount(int hourCount) {
        this.hourCount = hourCount;
    }

    public int getHourCount(){
        return hourCount;
    }
}
