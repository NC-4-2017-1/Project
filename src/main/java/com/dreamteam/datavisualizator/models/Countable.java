package com.dreamteam.datavisualizator.models;


import java.math.BigDecimal;
import java.util.Map;

public interface Countable {
    Map<Graphic, Correlation> getCorrelation();
    BigDecimal getAverage();
    BigDecimal getOlympicAverage();
    BigDecimal getDispersion();
    BigDecimal getMathExpectation();
}
