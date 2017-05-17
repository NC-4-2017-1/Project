package com.dreamteam.datavisualizator.models;


import java.math.BigInteger;

public enum UserTypes {
    REGULAR_USER(BigInteger.valueOf(1L)),
    ADMIN(BigInteger.valueOf(2L));

    private BigInteger id;

    UserTypes(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

}
