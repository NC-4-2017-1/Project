package com.dreamteam.datavisualizator.models.impl;

import com.dreamteam.datavisualizator.models.Selector;
import com.google.gson.JsonObject;

import java.math.BigInteger;

public abstract class AbstractSelector implements Selector {
    protected String value;
    protected String name;
    protected BigInteger id;

    public String getValue(){
       return  this.value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public BigInteger getId(){ return this.id;}

    public void setId(BigInteger id) { this.id = id;}
}
