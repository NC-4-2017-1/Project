package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HealthMonitorProject extends AbstractProject {
    private Map<BigInteger, Selector> selectors;
    private Graphic graphic;
    private String sid;
    private String port;
    private String serverName;
    private String userName;
    private String password;
    private String authorFullName;

    private HealthMonitorProject(Builder builder) {
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
        this.selectors = builder.selectors;
        this.graphic = builder.graphic;
        this.setType(ProjectTypes.HEALTH_MONITORING);
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

    public String getAuthorFullName() {      return authorFullName;   }

    public void setAuthorFullName(String authorFullName) {      this.authorFullName = authorFullName;   }

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
        private String authorFullName;

        private List<User> usersProjectAccessible;
        private Map<BigInteger, Selector> selectors;
        private Graphic graphic;

        public Builder(BigInteger id, String name, Date creationDate, String description, BigInteger author, String authorFullName){
            this.id = id;
            this.name = name;
            this.creationDate = creationDate;
            this.description = description;
            this.author = author;
            this.authorFullName = authorFullName;
        }

        public Builder(BigInteger id, String name, Date creationDate, String description, BigInteger author, String authorFullName,
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
            this.authorFullName = authorFullName;
        }

        public Builder buildDescription(String val) {
            description = val;
            return this;
        }

        public Builder buildUsersProjectAccessible(List<User> val) {
            usersProjectAccessible = val;
            return this;
        }

        public Builder buildSelectors(Map<BigInteger, Selector> val) {
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
