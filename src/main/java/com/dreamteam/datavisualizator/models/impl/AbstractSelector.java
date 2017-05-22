package com.dreamteam.datavisualizator.models.impl;

import com.dreamteam.datavisualizator.models.Selector;
import com.google.gson.JsonObject;

public abstract class AbstractSelector implements Selector {
    protected String value;
    protected String name;

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
}
