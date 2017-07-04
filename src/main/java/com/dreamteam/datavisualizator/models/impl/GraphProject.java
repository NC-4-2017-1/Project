package com.dreamteam.datavisualizator.models.impl;

import com.dreamteam.datavisualizator.models.GraphType;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.User;
import com.google.gson.JsonObject;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class GraphProject extends AbstractProject {
    private String sid;
    private String port;
    private String serverName;
    private String userName;
    private String password;
    private BigInteger idFirstElemGraph;
    private BigInteger referenceAttributeGraph;
    private JsonObject graphJson;
    private GraphType graphType;


    private GraphProject(GraphBuilder builder) {
        this.setId(builder.id);
        this.setName(builder.name);
        this.setCreationDate(builder.creationDate);
        this.setDescription(builder.description);
        this.setAuthor(builder.author);
        this.setAuthorFullName(builder.authorFullName);
        this.setSid(builder.sid);
        this.setPort(builder.port);
        this.setServerName(builder.serverName);
        this.setUserName(builder.userName);
        this.setPassword(builder.password);
        this.setUsersProjectAccessible(builder.usersProjectAccessible);
        this.setIdFirstElemGraph(builder.idFirstElemGraph);
        this.setType(ProjectTypes.GRAPH);
        this.setGraphJson(builder.graphJson);
        this.setGraphType(builder.graphType);
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public BigInteger getIdFirstElemGraph() {
        return idFirstElemGraph;
    }

    public void setIdFirstElemGraph(BigInteger idFirstElemGraph) {
        this.idFirstElemGraph = idFirstElemGraph;
    }

    public BigInteger getReferenceAttributeGraph() {
        return referenceAttributeGraph;
    }

    public void setReferenceAttributeGraph(BigInteger referenceAttributeGraph) {
        this.referenceAttributeGraph = referenceAttributeGraph;
    }

    public JsonObject getGraphJson() {
        return graphJson;
    }

    public void setGraphJson(JsonObject graphJson) {
        this.graphJson = graphJson;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }

    public static class GraphBuilder {
        private BigInteger id;
        private String name;
        private Date creationDate;
        private String description;
        private BigInteger author;
        private String sid;
        private String port;
        private String serverName;
        private String userName;
        private String password;
        private String authorFullName;
        private List<User> usersProjectAccessible;
        private BigInteger idFirstElemGraph;
        private BigInteger referenceAttributeGraph;
        private JsonObject graphJson;
        private GraphType graphType;


        public GraphBuilder(BigInteger id, String name, Date creationDate, String description, BigInteger author, String authorFullName) {
            this.id = id;
            this.name = name;
            this.creationDate = creationDate;
            this.description = description;
            this.author = author;
            this.authorFullName = authorFullName;

        }

        public GraphBuilder(BigInteger id, String name, Date creationDate, String description, BigInteger author, String authorFullName,
                            String sid, String port, String serverName, String userName, String password, BigInteger idFirstElemGraph, JsonObject graphJson) {
            this.id = id;
            this.name = name;
            this.creationDate = creationDate;
            this.description = description;
            this.author = author;
            this.authorFullName = authorFullName;
            this.sid = sid;
            this.port = port;
            this.serverName = serverName;
            this.userName = userName;
            this.password = password;
            this.author = author;
            this.authorFullName = authorFullName;
            this.idFirstElemGraph = idFirstElemGraph;
            this.graphJson = graphJson;
        }

        public GraphBuilder buildReferenceAttribute(BigInteger val) {
            referenceAttributeGraph = val;
            return this;
        }

        public GraphBuilder buildUsersProjectAccessible(List<User> val) {
            usersProjectAccessible = val;
            return this;
        }

        public GraphBuilder buildGraphType(GraphType val) {
            graphType = val;
            return this;
        }

        public GraphProject buildProject() {
            return new GraphProject(this);
        }

    }
}
