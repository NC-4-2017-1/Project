package com.dreamteam.datavisualizator.services.graphService;

import com.dreamteam.datavisualizator.dao.GraphDAO;
import org.apache.commons.graph.Vertex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GraphByReferenceSimpleDB extends AbstractGraph {
    private static final Logger LOGGER = Logger.getLogger(GraphByReferenceSimpleDB.class);
    @Autowired
    private GraphDAO graphDAO;

    private Set<Vertex> vertices;
    private Set<EdgeBetweenObject> edges;
    private BigInteger idFirstElement;
    private BigInteger idAttributesReference;

    public GraphByReferenceSimpleDB(BigInteger idFirstElement, BigInteger idAttributesReference) {
        this.idFirstElement = idFirstElement;
        this.idAttributesReference = idAttributesReference;
        vertices = new LinkedHashSet<>();
        edges = new LinkedHashSet<>();
        buildGraph(this.idFirstElement);
    }

    private void buildGraph(BigInteger id) {
        ObjectGraph objectGraph = graphDAO.getObjectGraphSimpleDB(id);
        if (objectGraph != null) {
            vertices.add(objectGraph);
            List<ObjectGraph> children = graphDAO.getObjectsByReferSimpleDB(id, idAttributesReference);
            for (ObjectGraph child : children) {
                edges.add(new EdgeBetweenObject(objectGraph, child));
                buildGraph(child.getId());
            }
        }
    }

    @Override
    public Set<Vertex> getVertices() {
        return vertices;
    }

    @Override
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