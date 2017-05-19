package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.HealthMonitorProject;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface HealthMonitorProjectDAO {

    Graphic getProjectGraphic(Project project);
    List<Selector> getProjectSelectors(Project project);
    Map<String, String> getProjectConnections(Project project);
    Graphic createGraphic(int hourCount);
    List<Selector> createSelectorList(Map<String, String> map);
    //boolean saveGraphic(int hourCount,Graphic graphic);
    //boolean saveSelector(Map<String,String> map, Selector selector);

    Project getProjectById(BigInteger id);
    Project getProjectByName(String projectName);
    List<HealthMonitorProject> getProjectsByAuthor(User user);
    List<HealthMonitorProject> getProjectsUserHaveAccessTo(User user);
    boolean deleteProject(Project project);
    boolean saveProject(Project project);

}
