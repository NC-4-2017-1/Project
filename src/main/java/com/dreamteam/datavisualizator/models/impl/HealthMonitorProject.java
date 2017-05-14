package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HealthMonitorProject extends AbstractProject{
    private Map<String, String> connectionParameters;
    private List<Selector> selectors;
    private Graphic graphic;

    public static class Builder{
        private String name;
        private Date creationDate;
        private BigInteger author;
        private String description;
        private Collection<User> usersProjectAccessible;
        private Map<String, String> connectionParameters;
        private List<Selector> selectors;
        private Graphic graphic;

        public Builder(String name, Date creationDate, BigInteger author){
            this.name = name;
            this.creationDate = creationDate;
            this.author = author;
        }
        public Builder description(String val){
            description = val;
            return this;
        }
        public Builder usersProjectAccessible(Collection<User> val){
            usersProjectAccessible = val;
            return this;
        }
        public Builder connectionParameters(Map<String, String> val){
            connectionParameters = val;
            return this;
        }
        public Builder selectors(List<Selector>  val){
            selectors = val;
            return this;
        }
        public Builder graphic(Graphic val){
            graphic = val;
            return this;
        }

        public HealthMonitorProject build(){
            return new HealthMonitorProject(this);
        }
    }

    private HealthMonitorProject(Builder builder){
        this.setName(builder.name);
        this.setCreationDate(builder.creationDate);
        this.setAuthor(builder.author);
        this.setDescription(builder.description);
        this.setUsersProjectAccessible(builder.usersProjectAccessible);
        this.connectionParameters = builder.connectionParameters;
        this.selectors = builder.selectors;
        this.graphic = builder.graphic;
    }

    public Map<String, String> getConnectionParameters() {
        return connectionParameters;
    }

    public void setConnectionParameters(Map<String, String> connectionParameters) {
        this.connectionParameters = connectionParameters;
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
}
