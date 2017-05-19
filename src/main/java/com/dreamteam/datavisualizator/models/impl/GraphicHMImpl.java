package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.google.gson.JsonObject;

import java.math.BigInteger;

public class GraphicHMImpl implements Graphic{

    private BigInteger id;
    private JsonObject graphicJSON;
    private int hourCount;
    private String name;

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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private GraphicHMImpl(HMGraphBuilder builder){
        this.id=builder.id;
        this.graphicJSON=builder.graphicJSON;
        this.hourCount = builder.hourCount;
        this.name = builder.name;
    }

    public static class HMGraphBuilder{
        private BigInteger id;
        private JsonObject graphicJSON;
        private int hourCount;
        private String name;

        public HMGraphBuilder(){
        }

        public HMGraphBuilder buildId(BigInteger val){
            id = val;
            return this;
        }

        public HMGraphBuilder buildGraphicJson(JsonObject val){
            graphicJSON = val;
            return this;
        }
        public HMGraphBuilder buildHourCount(Integer val){
            hourCount = val;
            return this;
        }
        public HMGraphBuilder buildName(String val){
            name = val;
            return this;
        }

        public GraphicHMImpl build(){
            return new GraphicHMImpl(this);
        }
    }
}
