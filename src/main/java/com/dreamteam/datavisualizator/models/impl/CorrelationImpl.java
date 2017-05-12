package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Correlation;
import com.dreamteam.datavisualizator.models.Graphic;

import java.math.BigDecimal;

public class CorrelationImpl implements Correlation{
    private Graphic firstGraphic;
    private Graphic secondGraphic;
    private BigDecimal correlationValue;

    public void setFirstGraphic(Graphic firstGraphic) {
        this.firstGraphic = firstGraphic;
    }

    public Graphic getFirstGraphic(){
        return firstGraphic;
    }

    public void setSecondGraphic(Graphic secondGraphic) {
        this.secondGraphic = secondGraphic;
    }

    public Graphic getSecondGraphic(){
        return secondGraphic;
    }

    public void setCorrelationValue(BigDecimal correlationValue) {
        this.correlationValue = correlationValue;
    }

    public BigDecimal getCorrelation(){
        return correlationValue;
    }
}
