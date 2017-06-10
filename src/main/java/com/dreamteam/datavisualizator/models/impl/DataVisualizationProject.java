package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class DataVisualizationProject extends AbstractProject {
    private List<Graphic> graphics;
    private String authorFullName;

    private DataVisualizationProject(Builder builder) {
        this.setId(builder.id);
        this.setName(builder.name);
        this.setCreationDate(builder.creationDate);
        this.setAuthor(builder.author);
        this.setAuthorFullName(builder.authorFullName);
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

    public String getAuthorFullName() {       return this.authorFullName;   }

    public void setAuthorFullName(String authorFullName) {      this.authorFullName = authorFullName;   }

    public static class Builder {
        private BigInteger id;
        private String name;
        private Date creationDate;
        private BigInteger author;
        private String authorFullName;
        private String description;
        private List<User> usersProjectAccessible;
        private List<Graphic> graphics;

        public Builder(String name, Date creationDate, BigInteger author, String authorFullName) {
            this.name = name;
            this.creationDate = creationDate;
            this.author = author;
            this.authorFullName = authorFullName;
        }

        public Builder buildId(BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder buildDescription(String val) {
            description = val;
            return this;
        }

        public Builder buildUsersProjectAccessible(List<User> val) {
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
