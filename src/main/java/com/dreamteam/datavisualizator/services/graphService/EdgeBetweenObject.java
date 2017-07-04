package com.dreamteam.datavisualizator.services.graphService;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;

public class EdgeBetweenObject implements Edge {
    private ObjectGraph header;
    private ObjectGraph tail;

    public EdgeBetweenObject(ObjectGraph header, ObjectGraph tail) {
        this.header = header;
        this.tail = tail;
    }

    public ObjectGraph getHeader() {
        return header;
    }

    public ObjectGraph getTail() {
        return tail;
    }

    public boolean isHeader(Vertex vertex) {
        if (header.equals(vertex)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTail(Vertex vertex) {
        if (tail.equals(vertex)) {
            return true;
        } else {
            return false;
        }
    }

}
