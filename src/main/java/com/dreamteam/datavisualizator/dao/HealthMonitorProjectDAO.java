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

    Graphic getProjectGraph(Project project);
    List<Selector> getProjectSelectors(Project project);
    Graphic createGraph(int hourCount);
    List<Selector> createSelectorList(Map<BigInteger, String> map);

    Project getProjectById(BigInteger id);
    Project getProjectByName(String projectName);
    List<HealthMonitorProject> getProjectsByAuthor(User user);
    List<HealthMonitorProject> getProjectsUserHaveAccessTo(User user);
    boolean deleteProject(Project project);
    boolean saveProject(Project project);
    List<User> getUsersThatHaveAccessToProject(Project project);

}
