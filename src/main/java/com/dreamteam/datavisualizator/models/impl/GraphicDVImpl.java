package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Correlation;
import com.dreamteam.datavisualizator.models.Calculable;
import com.dreamteam.datavisualizator.models.Graphic;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class GraphicDVImpl implements Graphic, Calculable {

    private BigInteger id;
    private String name;
    private JsonObject graphicJSON;
    private Map<Graphic, Correlation> correlation;
    private BigDecimal average;
    private BigDecimal olympicAverage;
    private BigDecimal dispersion;
    private BigDecimal mathExpectation;

    private GraphicDVImpl(DVGraphBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.graphicJSON = builder.graphicJSON;
        this.correlation = builder.correlation;
        this.average = builder.average;
        this.olympicAverage = builder.olympicAverage;
        this.dispersion = builder.dispersion;
        this.mathExpectation = builder.mathExpectation;
    }


    public JsonObject getGraphicJSON() {
        return graphicJSON;
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public static class DVGraphBuilder {
        private BigInteger id;
        private String name;
        private JsonObject graphicJSON;
        private Map<Graphic, Correlation> correlation;
        private BigDecimal average;
        private BigDecimal olympicAverage;
        private BigDecimal dispersion;
        private BigDecimal mathExpectation;

        public DVGraphBuilder() {
        }

        public DVGraphBuilder buildId(BigInteger val) {
            id = val;
            return this;
        }

        public DVGraphBuilder buildName(String val) {
            name = val;
            return this;
        }

        public DVGraphBuilder buildGraphicJSON(JsonObject val) {
            graphicJSON = val;
            return this;
        }

        public DVGraphBuilder buildCorrelation(Map<Graphic, Correlation> val) {
            correlation = val;
            return this;
        }

        public DVGraphBuilder buildAverage(BigDecimal val) {
            average = val;
            return this;
        }

        public DVGraphBuilder buildOlympicAverage(BigDecimal val) {
            olympicAverage = val;
            return this;
        }

        public DVGraphBuilder buildDispersion(BigDecimal val) {
            dispersion = val;
            return this;
        }

        public DVGraphBuilder buildMathExpectation(BigDecimal val) {
            mathExpectation = val;
            return this;
        }

        public GraphicDVImpl build() {
            return new GraphicDVImpl(this);
        }

    }
}
