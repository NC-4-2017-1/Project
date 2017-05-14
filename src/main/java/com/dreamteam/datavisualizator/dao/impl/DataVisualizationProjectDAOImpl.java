package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class DataVisualizationProjectDAOImpl implements DataVisualizationProjectDAO {
    public Project getProjectById(BigInteger id) {
        return null;
    }

    public Project getProjectByName(String projectName) {
        return null;
    }

    public List<Project> getProjectsByAuthor(User user) {
        return null;
    }

    public List<Project> getProjectsUserHaveAccessTo(User user) {
        return null;
    }

    public boolean deleteProject(Project project) {
        return false;
    }

    public boolean saveProject(Project project) {
        return false;
    }

    public List<Graphic> getProjectGraphics(Project project) {
        return null;
    }

    public void getPreviewProjectDataForUser(User user) {

    }
}
