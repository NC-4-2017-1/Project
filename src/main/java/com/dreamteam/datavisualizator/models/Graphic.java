package com.dreamteam.datavisualizator.models;


import com.google.gson.JsonObject;

import java.math.BigInteger;

public interface Graphic {
    JsonObject getGraphicJSON();

    BigInteger getId();

    String getName();
}
