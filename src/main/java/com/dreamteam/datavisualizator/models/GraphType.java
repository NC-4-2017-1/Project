package com.dreamteam.datavisualizator.models;

public enum GraphType {
    SIMPLE_DATA_BASE_BY_PARENT("Simple date base by parent"),
    SIMPLE_DATA_BASE_BY_REFERENCE("Simple date base by reference"),
    NC_DATE_BASE_BY_PARENT("NC date base by parent"),
    NC_DATE_BASE_BY_REFERENCE("NC date base by reference");

    private final String graphTypeName;

    GraphType(String graphTypeName) {
        this.graphTypeName = graphTypeName;
    }

    @Override
    public String toString() {
        return graphTypeName;
    }
}
