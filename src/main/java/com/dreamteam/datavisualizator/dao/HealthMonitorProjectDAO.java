package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HealthMonitorProjectDAO {

    Graphic getProjectGraphic(Project project);
    List<Selector> getProjectSelectors(Project project);
    Map<String,String> getProjectConnectionParameters(Project project);
    List<Date> getDataForGraphic(Map<String,String> map);
    List<Date> getDataForSelector(Map<String,String> map);
    boolean saveGraphic(Map<String,String> map,Graphic graphic);
    boolean saveSelector(Map<String,String> map, Selector selector);

    Project getProjectById(BigInteger id);
    Project getProjectByName(String projectName);
    List<Project> getProjectsByAuthor(User user);
    List<Project> getProjectsUserHaveAccessTo(User user);
    boolean deleteProject(Project project);
    boolean saveProject(Project project);
    void getPreviewProjectDataForUser(User user);

}