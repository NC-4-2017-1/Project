package com.dreamteam.datavisualizator.models;


import java.math.BigInteger;

public enum UserTypes {
    REGULAR_USER(BigInteger.valueOf(1L), "ROLE_REGULAR_USER"),
    ADMIN(BigInteger.valueOf(2L), "ROLE_ADMIN");

    private final BigInteger id;
    private final String userRole;

    UserTypes(BigInteger id, String userRole) {
        this.id = id;
        this.userRole = userRole;
    }

    public static UserTypes getRoleById(BigInteger id) {
        for (UserTypes e : UserTypes.values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;// not found
    }

    public BigInteger getId() {
        return id;
    }

    @Override
    public String toString() {
        return userRole;
    }
}
