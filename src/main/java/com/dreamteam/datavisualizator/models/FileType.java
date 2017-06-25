package com.dreamteam.datavisualizator.models;

public enum FileType {
    CSV("csv"),
    XML("xml");

    private final String name;

    FileType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static FileType getType(String typeName){
        for (FileType fileType : values()) {
            if(fileType.getName().equals(typeName)){
                return fileType;
            }
        }
        return null;
    }
}
