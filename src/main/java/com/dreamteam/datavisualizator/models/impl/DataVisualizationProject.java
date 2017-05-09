package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class DataVisualizationProject extends AbstractProject{
    private List<Graphic> graphics;

    DataVisualizationProject(BigInteger id, String name, Date creationDate, User author, String description, ProjectTypes type,
                             List<Graphic> graphics){
        this.setId(id);
        this.setName(name);
        this.setCreationDate(creationDate);
        this.setAuthor(author);
        this.setDescription(description);
        this.setType(type);
        this.setGraphics(graphics);
    }

    public List<Graphic> getGraphics() {
        return graphics;
    }

    public void setGraphics(List<Graphic> graphics) {
        this.graphics = graphics;
    }
}
