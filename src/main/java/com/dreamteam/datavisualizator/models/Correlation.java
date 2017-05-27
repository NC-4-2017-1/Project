package com.dreamteam.datavisualizator.models;


import java.math.BigDecimal;
import java.math.BigInteger;

public interface Correlation {
    Graphic getFirstGraphic();
    Graphic getSecondGraphic();
    BigDecimal getCorrelation();
    BigInteger getId();
    String getName();
}
