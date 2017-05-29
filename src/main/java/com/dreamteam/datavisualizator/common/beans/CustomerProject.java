package com.dreamteam.datavisualizator.common.beans;

import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.Selector;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class CustomerProject {

    private ProjectTypes type;
    private String name;
    private String description;
    private List<Graphic> graphics;
    private Map<BigInteger, Selector> selectors;
    private Graphic graphic;
    private String serverName;
    private String port;
    private String sid;
    private String userName;
    private String password;

    public ProjectTypes getType() {
        return type;
    }

    public void setType(ProjectTypes type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Graphic> getGraphics() {
        return graphics;
    }

    public void setGraphics(List<Graphic> graphics) {
        this.graphics = graphics;
    }

    public Map<BigInteger, Selector> getSelectors() {
        return selectors;
    }

    public void setSelectors(Map<BigInteger, Selector> selectors) {
        this.selectors = selectors;
    }

    public Graphic getGraphic() {
        return graphic;
    }

    public void setGraphic(Graphic graphic) {
        this.graphic = graphic;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
}
