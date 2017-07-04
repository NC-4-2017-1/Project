package com.dreamteam.datavisualizator.services.graphService;

import com.dreamteam.datavisualizator.dao.GraphDAO;
import org.apache.commons.graph.Vertex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GraphByParentNCDB extends AbstractGraph {
    private static final Logger LOGGER = Logger.getLogger(GraphByParentSimpleDB.class);
    @Autowired
    private GraphDAO graphDAO;

    private Set<Vertex> vertices;
    private Set<EdgeBetweenObject> edges;
    private BigInteger idFirstElement;

    public GraphByParentNCDB(BigInteger idFirstElement) {
        vertices = new LinkedHashSet<>();
        edges = new LinkedHashSet<>();
        this.idFirstElement = idFirstElement;
        LOGGER.info("Building graph by parent, scheme NCDB. The first id is "+ idFirstElement);
        buildGraph(idFirstElement);
    }

    private void buildGraph(BigInteger id) {
        List<ObjectGraph> objectsGraph = graphDAO.getObjectsByParentNCDB(id);
        for (ObjectGraph objectGraph : objectsGraph) {
            vertices.add(objectGraph);
            edges.add(new EdgeBetweenObject(getParent(objectsGraph, objectGraph.getParentId()), objectGraph));
        }
    }
}
