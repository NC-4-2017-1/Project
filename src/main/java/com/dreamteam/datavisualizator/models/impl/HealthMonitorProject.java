package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HealthMonitorProject extends AbstractProject{
    private Map<String, String> connectionParameters;
    private List<Selector> selectors;
    private Graphic graphic;

    HealthMonitorProject(BigInteger id, String name, Date creationDate, User author, String description, ProjectTypes type,
                         Map<String, String> connectionParameters, List<Selector> selectors, Graphic graphic){
        this.setId(id);
        this.setName(name);
        this.setCreationDate(creationDate);
        this.setAuthor(author);
        this.setDescription(description);
        this.setType(type);
        this.setConnectionParameters(connectionParameters);
        this.setSelectors(selectors);
        this.setGraphic(graphic);
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
