package com.dreamteam.datavisualizator.services.graphService;

import com.dreamteam.datavisualizator.dao.GraphDAO;
import org.apache.commons.graph.Vertex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GraphByReferenceNCDB extends AbstractGraph {
    private static final Logger LOGGER = Logger.getLogger(GraphByReferenceNCDB.class);
    @Autowired
    private GraphDAO graphDAO;

    private Set<Vertex> vertices;
    private Set<EdgeBetweenObject> edges;
    private BigInteger idFirstElement;
    private BigInteger idAttributesReference;

    public GraphByReferenceNCDB(BigInteger idFirstElement, BigInteger idAttributesReference) {
        this.idFirstElement = idFirstElement;
        this.idAttributesReference = idAttributesReference;
        vertices = new LinkedHashSet<>();
        edges = new LinkedHashSet<>();
        buildGraph(idFirstElement);
    }

    private void buildGraph(BigInteger id) {
        ObjectGraph objectGraph = graphDAO.getObjectGraphNCDB(id);
        if (objectGraph != null) {
            vertices.add(objectGraph);
            List<ObjectGraph> children = graphDAO.getObjectsByReferNCDB(id, idAttributesReference);
            for (ObjectGraph child : children) {
                edges.add(new EdgeBetweenObject(objectGraph, child));
                buildGraph(child.getId());
            }
        }
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public Set<EdgeBetweenObject> getEdges() {
        return edges;
    }

    public BigInteger getIdFirstElement() {
        return idFirstElement;
    }

    public BigInteger getIdAttributesReference() {
        return idAttributesReference;
    }
}
