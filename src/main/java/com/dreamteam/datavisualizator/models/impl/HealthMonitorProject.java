package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class HealthMonitorProject extends AbstractProject {
    private List<Selector> selectors;
    private Graphic graphic;
    private String sid;
    private String port;
    private String serverName;
    private String userName;
    private String password;

    private HealthMonitorProject(Builder builder) {
        this.setId(builder.id);
        this.setName(builder.name);
        this.setCreationDate(builder.creationDate);
        this.setDescription(builder.description);
        this.setAuthor(builder.author);
        this.setSid(builder.sid);
        this.setPort(builder.port);
        this.setServerName(builder.serverName);
        this.setUserName(builder.userName);
        this.setPassword(builder.password);
        this.setUsersProjectAccessible(builder.usersProjectAccessible);
        this.selectors = builder.selectors;
        this.graphic = builder.graphic;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return this.sid;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return this.port;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return this.serverName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public List<Selector> getSelectors() {
        return selectors;
    }

    public void setSelectors(List<Selector> selectors) {
        this.selectors = selectors;
    }

    public Graphic getGraphic() {
        return graphic;
    }

    public void setGraphic(Graphic graphic) {
        this.graphic = graphic;
    }

    public static class Builder {
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

        private List<User> usersProjectAccessible;
        private List<Selector> selectors;
        private Graphic graphic;

        public Builder(BigInteger id, String name, Date creationDate, String description, BigInteger author,
                       String sid, String port, String serverName, String userName, String password) {
            this.id = id;
            this.name = name;
            this.creationDate = creationDate;
            this.description = description;
            this.sid = sid;
            this.port = port;
            this.serverName = serverName;
            this.userName = userName;
            this.password = password;
            this.author = author;
        }

        public Builder buildDescription(String val) {
            description = val;
            return this;
        }

        public Builder buildUsersProjectAccessible(List<User> val) {
            usersProjectAccessible = val;
            return this;
        }

        public Builder buildSelectors(List<Selector> val) {
            selectors = val;
            return this;
        }

        public Builder buildGraphic(Graphic val) {
            graphic = val;
            return this;
        }

        public HealthMonitorProject buildProject() {
            return new HealthMonitorProject(this);
        }
    }
}
