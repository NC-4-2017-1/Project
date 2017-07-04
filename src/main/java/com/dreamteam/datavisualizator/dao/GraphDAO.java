package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.GraphProject;
import com.dreamteam.datavisualizator.services.graphService.ObjectGraph;

import java.math.BigInteger;
import java.util.List;

public interface GraphDAO {
    void setDataSourceTemplate(String serverName, String port, String sid, String username, String password);

    String getProjectName(BigInteger id);

    Project getProjectById(BigInteger id);

    Project getProjectByName(String name);

    List<GraphProject> getProjectsByAuthor(User user);

    List<GraphProject> getProjectsUserHaveAccessTo(User user);

    boolean deleteProject(Project project);

    Project saveProject(Project project);

    ObjectGraph getObjectGraphSimpleDB(BigInteger idFirstElement);

    List<ObjectGraph> getObjectsByReferSimpleDB(BigInteger idFirstElement, BigInteger idAttributesReference);

    ObjectGraph getObjectGraphNCDB(BigInteger idFirstElement);

    List<ObjectGraph> getObjectsByReferNCDB(BigInteger idFirstElement, BigInteger idAttributesReference);

    List<ObjectGraph> getObjectsByParentSimpleDB(BigInteger idFirstElement);

    List<ObjectGraph> getObjectsByParentNCDB(BigInteger idFirstElement);
}
