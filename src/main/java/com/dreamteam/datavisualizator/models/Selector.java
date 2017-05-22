package com.dreamteam.datavisualizator.models;


import com.google.gson.JsonObject;

public interface Selector {
    String getValue();
    void setValue(String value);
    String getName();
    void setName(String name);
}
