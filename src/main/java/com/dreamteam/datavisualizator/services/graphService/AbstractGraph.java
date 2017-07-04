package com.dreamteam.datavisualizator.services.graphService;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractGraph implements org.apache.commons.graph.DirectedGraph {
    private static final Logger LOGGER = Logger.getLogger(AbstractGraph.class);

    private Set<Vertex> vertices;

    private Set<EdgeBetweenObject> edges;


    @Override
    public Set getInbound(Vertex vertex) {
        Set<EdgeBetweenObject> linksToParent = new LinkedHashSet<>();
        for (EdgeBetweenObject edge : edges) {
            if (edge.isTail(vertex)) {
                linksToParent.add(edge);
            }
        }
        return linksToParent;// ito parent
    }

    @Override
    public Set getOutbound(Vertex vertex) {
        Set<EdgeBetweenObject> linksToChildren = new LinkedHashSet<>();
        for (EdgeBetweenObject edge : edges) {
            if (edge.isHeader(vertex)) {
                linksToChildren.add(edge);
            }
        }
        return linksToChildren; //to children
    }

    @Override
    public Vertex getSource(Edge edge) {
        EdgeBetweenObject edgeBetweenObject = (EdgeBetweenObject) edge;
        return edgeBetweenObject.getHeader();// get head
    }

    @Override
    public Vertex getTarget(Edge edge) {
        EdgeBetweenObject edgeBetweenObject = (EdgeBetweenObject) edge;
        return edgeBetweenObject.getTail();// get tail
    }

    @Override
    public Set getVertices() {
        return vertices;//all vertices in graph
    }

    @Override
    public Set getEdges() {
        return edges; //all edge in graph
    }

    @Override
    public Set getEdges(Vertex vertex) {
        Set<EdgeBetweenObject> edgeOfVertex = new LinkedHashSet<>();
        for (EdgeBetweenObject edgeBetweenObject : edges) {
            if (edgeBetweenObject.isTail(vertex) || edgeBetweenObject.isHeader(vertex)) {
                edgeOfVertex.add(edgeBetweenObject);
            }
        }
        return edgeOfVertex;//set edge which consist vertex
    }

    @Override
    public Set getVertices(Edge edge) {
        EdgeBetweenObject edgeBetweenObject = (EdgeBetweenObject) edge;
        Set<Vertex> vertices = new LinkedHashSet<>();
        vertices.add(edgeBetweenObject.getHeader());
        vertices.add(edgeBetweenObject.getTail());
        return vertices;//get vertices which in edge
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setEdges(Set<EdgeBetweenObject> edges) {
        this.edges = edges;
    }

    public ObjectGraph getParent(List<ObjectGraph> objectGraphs, BigInteger id){
        for(ObjectGraph objectGraph: objectGraphs){
            if(objectGraph.getId().equals(id)){
                return objectGraph;
            }
        }
        return null;
    }
}
