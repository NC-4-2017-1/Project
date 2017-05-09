package com.dreamteam.datavisualizator.models;


import java.math.BigDecimal;

public interface Correlation {
    Graphic getFirstGraphic();
    Graphic getSecondGraphic();
    BigDecimal getCorrelation();
}
