package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;

import java.math.BigInteger;
import java.util.List;

public interface DataVisualizationProjectDAO {
    Project getProjectById(BigInteger id);

    Project getProjectByName(String projectName);

    List<DataVisualizationProject> getProjectsByAuthor(User user);

    List<DataVisualizationProject> getProjectsUserHaveAccessTo(User user);

    boolean deleteProject(Project project);

    Project saveProject(String name, BigInteger authorId, String description, List<Graphic> graphics);

    List<Graphic> getProjectGraphs(Project project);
}
