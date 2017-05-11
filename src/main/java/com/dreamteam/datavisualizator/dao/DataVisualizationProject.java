package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.List;

public interface DataVisualizationProject {
    Project getProjectById(BigInteger id);
    Project getProjectByName(String projectName);
    List<Project> getProjectsByAuthor(User user);
    List<Project> getProjectsUserHaveAccessTo(User user);
    boolean deleteProject(Project project);
    boolean saveProject(Project project);
    List<Graphic> getProjectGraphics(Project project);
    void getPreviewProjectDataForUser(User user);
}
