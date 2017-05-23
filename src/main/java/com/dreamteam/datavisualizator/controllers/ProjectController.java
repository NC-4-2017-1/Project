package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    DataVisualizationProjectDAO projectDAO;
    @Autowired
    DataVisualizationProjectDAO healthMonitorProjectDAOImpl;

    @RequestMapping(path = "/project-view", method = RequestMethod.GET)
    public String projectView(Model model) {
        return "project";
    }

    @RequestMapping(path = "/visualization-setup", method = RequestMethod.GET)
    public String visualizationProjectSetup(Model model) {
        return "dataVisualizationProjectInitialSetup";
    }

    @RequestMapping(path = "/health-monitor-setup", method = RequestMethod.GET)
    public String healthMonitorSetup(Model model) {
        return "healthMonitorProjectInitialSetup";
    }

    @RequestMapping(path = "/visualization-settings", method = RequestMethod.GET)
    public String visualizationProjectSettings(Model model) {
        return "dataVisualizationAdvancedSettings";
    }

    @RequestMapping(path = "/health-monitor-settings", method = RequestMethod.GET)
    public String healthMonitorSettings(Model model) {
        return "healthMonitorAdvancedSettings";
    }

    @RequestMapping(path = "/get-by-id", method = RequestMethod.GET)
    public Project getProjectById(Model model) {
        return null;
    }

    @RequestMapping(path = "/get-by-name", method = RequestMethod.GET)
    public Project getProjectByName(Model model) {
        return null;
    }

    @RequestMapping(path = "/get-by-author", method = RequestMethod.GET)
    public Project getProjectsByAuthor(Model model) {
        return null;
    }

    @RequestMapping(path = "/get-project-user-have-access", method = RequestMethod.GET)
    public Project getProjectsUserHaveAccessTo(Model model) {
        return null;
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public void deleteProject(Project project,
                              Model model) {
        projectDAO.deleteProject(project);
    }

    @RequestMapping(path = "/save", method = RequestMethod.GET)
    public Project saveProject(Model model) {
        return null;
    }

    @RequestMapping(path = "/get-graphs", method = RequestMethod.GET)
    public List<Graphic> getProjectGraphs(Model model) {
        return null;
    }


}
