package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DataVisualizationProject extends AbstractProject {
    private List<Graphic> graphics;

    private DataVisualizationProject(Builder builder) {
        this.setId(builder.id);
        this.setName(builder.name);
        this.setCreationDate(builder.creationDate);
        this.setAuthor(builder.author);
        this.setDescription(builder.description);
        this.setUsersProjectAccessible(builder.usersProjectAccessible);
        this.setGraphics(builder.graphics);
        this.setType(ProjectTypes.DATA_VISUALIZATION);
    }

    public List<Graphic> getGraphics() {
        return graphics;
    }

    public void setGraphics(List<Graphic> graphics) {
        this.graphics = graphics;
    }

    public static class Builder {
        private BigInteger id;
        private String name;
        private Date creationDate;
        private BigInteger author;
        private String description;
        private Collection<User> usersProjectAccessible;
        private List<Graphic> graphics;

        public Builder(String name, Date creationDate, BigInteger author) {
            this.name = name;
            this.creationDate = creationDate;
            this.author = author;
        }

        public Builder buildId(BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder buildDescription(String val) {
            description = val;
            return this;
        }

        public Builder buildUsersProjectAccessible(Collection<User> val) {
            usersProjectAccessible = val;
            return this;
        }

        public Builder buildGraphics(List<Graphic> val) {
            graphics = val;
            return this;
        }

        public DataVisualizationProject buildProject() {
            return new DataVisualizationProject(this);
        }
    }

}
