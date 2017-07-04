package com.dreamteam.datavisualizator.services.graphService;

import org.apache.commons.graph.Vertex;

import java.math.BigInteger;

public class ObjectGraph implements Vertex {
    private BigInteger id;
    private String nameObject;
    private BigInteger parentId;
    private BigInteger level;


    public ObjectGraph(BigInteger id, String nameObject, BigInteger parentId, BigInteger level) {
        this.id = id;
        this.nameObject = nameObject;
        this.parentId = parentId;
        this.level = level;
    }

    public ObjectGraph(BigInteger id, String nameObject, BigInteger level) {
        this.id = id;
        this.nameObject = nameObject;
        this.level = level;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNameObject() {
        return nameObject;
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    public BigInteger getParentId() {

        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public BigInteger getLevel() {
        return level;
    }

    public void setLevel(BigInteger level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectGraph)) return false;

        ObjectGraph that = (ObjectGraph) o;

        if (!getId().equals(that.getId())) return false;
        if (!getNameObject().equals(that.getNameObject())) return false;
        return getParentId() != null ? getParentId().equals(that.getParentId()) : that.getParentId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getNameObject().hashCode();
        result = 31 * result + (getParentId() != null ? getParentId().hashCode() : 0);
        return result;
    }


}
