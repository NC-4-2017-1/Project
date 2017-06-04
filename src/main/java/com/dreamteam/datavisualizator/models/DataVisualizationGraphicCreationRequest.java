package com.dreamteam.datavisualizator.models;

public class DataVisualizationGraphicCreationRequest {
    private String[] xAxis;
    private String[] yAxis;

    public DataVisualizationGraphicCreationRequest() {
    }

    public String[] getxAxis() {
        return xAxis;
    }

    public void setxAxis(String[] xAxis) {
        this.xAxis = xAxis;
    }

    public String[] getyAxis() {
        return yAxis;
    }

    public void setyAxis(String[] yAxis) {
        this.yAxis = yAxis;
    }

}
