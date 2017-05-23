package com.dreamteam.datavisualizator.models;


import com.google.gson.JsonObject;

import java.math.BigInteger;

public interface Selector {
    String getValue();
    void setValue(String value);
    String getName();
    void setName(String name);
    BigInteger getId();
    void  setId(BigInteger id);
}
