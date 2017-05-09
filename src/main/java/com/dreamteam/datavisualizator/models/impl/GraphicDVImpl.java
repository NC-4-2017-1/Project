package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Correlation;
import com.dreamteam.datavisualizator.models.Countable;
import com.dreamteam.datavisualizator.models.Graphic;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.util.Map;

public class GraphicDVImpl implements Graphic,Countable{
    private JsonObject graphicJSON;
    private Map<Graphic, Correlation> correlation;
    private BigDecimal average;
    private BigDecimal olympicAverage;
    private BigDecimal dispersion;
    private BigDecimal mathExpectation;


    public JsonObject getGraphicJSON() {
        return graphicJSON;
    }

    public void setGraphicJSON(JsonObject graphicJSON) {
        this.graphicJSON = graphicJSON;
    }

    public Map<Graphic, Correlation> getCorrelation() {
        return correlation;
    }

    public void setCorrelation(Map<Graphic, Correlation> correlation) {
        this.correlation = correlation;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public BigDecimal getOlympicAverage() {
        return olympicAverage;
    }

    public void setOlympicAverage(BigDecimal olympicAverage) {
        this.olympicAverage = olympicAverage;
    }

    public BigDecimal getDispersion() {
        return dispersion;
    }

    public void setDispersion(BigDecimal dispersion) {
        this.dispersion = dispersion;
    }

    public BigDecimal getMathExpectation() {
        return mathExpectation;
    }

    public void setMathExpectation(BigDecimal mathExpectation) {
        this.mathExpectation = mathExpectation;
    }
}
