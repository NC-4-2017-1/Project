package com.dreamteam.datavisualizator.models;

public class DataVisualizationGraphicCreationRequest {
    private String[] xAxis;
    private String[] yAxis;
    private String[] mathCol;
    private String[] correlationCol;

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

    public String[] getmathCol(){
        return mathCol;
    }

    public void setmathCol(String[] mathCol){
        this.mathCol = mathCol;
    }

    public String[] getcorrelationCol() {
        return correlationCol;
    }

    public void setcorrelationCol(String[] correlationCol) {
        this.correlationCol = correlationCol;
    }

}
