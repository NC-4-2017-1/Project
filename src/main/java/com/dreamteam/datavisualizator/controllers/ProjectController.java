package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    DataVisualizationProjectDAO projectDAO;
    @Autowired
    DataVisualizationProjectDAO healthMonitorProjectDAOImpl;

    @RequestMapping(path = "/layout", method = RequestMethod.GET)
    public String projectView(Model model) {
        return "project";
    }

    @RequestMapping(path = "/share", method = RequestMethod.GET)
    public String shareProject(Model model) {
        return "shareProject";
    }

    @RequestMapping(path = "/create-layout", method = RequestMethod.GET)
    public String createProject(Model model) {
        return "projectCreation";
    }

    @RequestMapping(path = "/visualization-setup", method = RequestMethod.GET)
    public String visualizationProjectSetup(Model model) {
        return "dataVisualizationProjectInitialSetup";
    }

    @RequestMapping(path = "/visualization-settings", method = RequestMethod.GET)
    public String visualizationProjectSettings(Model model) {
        return "dataVisualizationAdvancedSettings";
    }

    @RequestMapping(path = "/health-monitor-setup", method = RequestMethod.GET)
    public String healthMonitorSetup(Model model) {
        return "healthMonitorProjectInitialSetup";
    }

    @RequestMapping(path = "/health-monitor-settings", method = RequestMethod.GET)
    public String healthMonitorSettings(Model model) {
        return "healthMonitorAdvancedSettings";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteProject(Project project,
                                 Model model) {
        return projectDAO.deleteProject(project);
    }

    @RequestMapping(path = "/save", method = RequestMethod.GET)
    @ResponseBody
    public Project saveProject(@RequestParam("name") String name,
                               @RequestParam("description") String description,
                               Model model) {
    // return projectDAO.saveProject();
    return null;
    }

}
