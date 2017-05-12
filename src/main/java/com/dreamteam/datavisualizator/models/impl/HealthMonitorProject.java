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
