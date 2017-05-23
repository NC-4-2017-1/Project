package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    DataVisualizationProjectDAO projectDAO;
    @Autowired
    DataVisualizationProjectDAO healthMonitorProjectDAOImpl;

    @RequestMapping(path = "/projectView", method = RequestMethod.GET)
    public String projectView(Model model) {
        return "project";
    }

    @RequestMapping(path = "/visualizationProjectSetup", method = RequestMethod.GET)
    public String visualizationProjectSetup(Model model) {
        return "dataVisualizationProjectInitialSetup";
    }

    @RequestMapping(path = "/healthMonitorProjectSetup", method = RequestMethod.GET)
    public String healthMonitorSetup(Model model) {
        return "healthMonitorProjectInitialSetup";
    }

    @RequestMapping(path = "/visualizationProjectSettings", method = RequestMethod.GET)
    public String visualizationProjectSettings(Model model) {
        return "dataVisualizationAdvancedSettings";
    }

    @RequestMapping(path = "/healthMonitorProjectSettings", method = RequestMethod.GET)
    public String healthMonitorSettings(Model model) {
        return "healthMonitorAdvancedSettings";
    }

    @RequestMapping(path = "/getById", method = RequestMethod.GET)
    public Project getProjectById(Model model) {
        return null;
    }

    @RequestMapping(path = "/getByName", method = RequestMethod.GET)
    public Project getProjectByName(Model model) {
        return null;
    }

    @RequestMapping(path = "/getByAuthor", method = RequestMethod.GET)
    public Project getProjectsByAuthor(Model model) {
        return null;
    }

    @RequestMapping(path = "/getUserHaveAccessTo", method = RequestMethod.GET)
    public Project getProjectsUserHaveAccessTo(Model model) {
        return null;
    }

    @RequestMapping(path = "/deleteProject", method = RequestMethod.GET)
    @ResponseBody
    public void deleteProject(Project project,
                              Model model) {
        projectDAO.deleteProject(project);
    }



}
