package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.google.gson.JsonObject;

public class GraphicHMImpl implements Graphic{
    private JsonObject graphicJSON;

    public void setGraphicJSON(JsonObject graphicJSON) {
        this.graphicJSON = graphicJSON;
    }

    public JsonObject getGraphicJSON(){
        return graphicJSON;
    }
}
