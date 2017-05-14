package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DataVisualizationProject extends AbstractProject{
    private List<Graphic> graphics;

    public static class Builder{
        private String name;
        private Date creationDate;
        private BigInteger author;
        private String description;
        private Collection<User> usersProjectAccessible;
        private List<Graphic> graphics;

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
        public Builder graphics(List<Graphic> val){
            graphics = val;
            return this;
        }

        public DataVisualizationProject build(){
            return new DataVisualizationProject(this);
        }
    }

    private DataVisualizationProject(Builder builder){
        this.setName(builder.name);
        this.setCreationDate(builder.creationDate);
        this.setAuthor(builder.author);
        this.setDescription(builder.description);
        this.setUsersProjectAccessible(builder.usersProjectAccessible);
        this.graphics = builder.graphics;
    }

    public List<Graphic> getGraphics() {
        return graphics;
    }

    public void setGraphics(List<Graphic> graphics) {
        this.graphics = graphics;
    }
}
