package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Correlation;
import com.dreamteam.datavisualizator.models.Graphic;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CorrelationImpl implements Correlation{
    private Graphic firstGraphic;
    private Graphic secondGraphic;
    private BigDecimal correlationValue;
    private  BigInteger id;
    private String name;

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

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
