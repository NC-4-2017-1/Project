package com.dreamteam.datavisualizator.services.graphService;

import com.dreamteam.datavisualizator.dao.GraphDAO;
import com.dreamteam.datavisualizator.dao.impl.GraphDAOImpl;
import org.apache.commons.graph.Vertex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GraphByParentSimpleDB extends AbstractGraph {
    private static final Logger LOGGER = Logger.getLogger(GraphByParentSimpleDB.class);
    @Autowired
    private GraphDAO graphDAO;

    private Set<Vertex> vertices;
    private Set<EdgeBetweenObject> edges;
    private BigInteger idFirstElement;

    public GraphByParentSimpleDB(BigInteger idFirstElement) {
        this.idFirstElement = idFirstElement;
        vertices = new LinkedHashSet<>();
        edges = new LinkedHashSet<>();
        buildGraph(this.idFirstElement);
        LOGGER.info("Building graph by parent, scheme Simple DB. The first id is "+ idFirstElement);
    }

    private void buildGraph(BigInteger id) {
        List<ObjectGraph> objectsGraph = graphDAO.getObjectsByParentSimpleDB(id);
        for (ObjectGraph objectGraph : objectsGraph) {
            vertices.add(objectGraph);
            edges.add(new EdgeBetweenObject(getParent(objectsGraph, objectGraph.getParentId()), objectGraph));
        }
    }

}
