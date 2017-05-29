package com.dreamteam.datavisualizator.models;


import java.math.BigInteger;

public enum ProjectTypes {
    DATA_VISUALIZATION(BigInteger.valueOf(4L), "Data Visualization"),
    HEALTH_MONITORING(BigInteger.valueOf(3L), "Health Monitoring");


    private final BigInteger id;
    private final String projectTypeName;


    ProjectTypes(BigInteger id, String projectTypeName) {
        this.id = id;
        this.projectTypeName = projectTypeName;
    }

    public static ProjectTypes getRoleById(BigInteger id) {
        for (ProjectTypes e : ProjectTypes.values()) {
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
        return projectTypeName;
    }
}
