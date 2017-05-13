package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HealthMonitorProjectDAOImpl implements HealthMonitorProjectDAO {
    public Graphic getProjectGraphic(Project project) {
        return null;
    }

    public List<Selector> getProjectSelectors(Project project) {
        return null;
    }

    public Map<String, String> getProjectConnectionParameters(Project project) {
        return null;
    }

    public List<Date> getDataForGraphic(Map<String, String> map) {
        return null;
    }

    public List<Date> getDataForSelector(Map<String, String> map) {
        return null;
    }

    public boolean saveGraphic(Map<String, String> map, Graphic graphic) {
        return false;
    }

    public boolean saveSelector(Map<String, String> map, Selector selector) {
        return false;
    }

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

    public void getPreviewProjectDataForUser(User user) {

    }
}
