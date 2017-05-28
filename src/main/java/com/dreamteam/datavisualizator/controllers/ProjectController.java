package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;


@Controller
@Secured("ROLE_REGULAR_USER")
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    DataVisualizationProjectDAO projectDAO;
    @Autowired
    DataVisualizationProjectDAO healthMonitorProjectDAOImpl;


    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/share", method = RequestMethod.GET)
    public String shareProject(Model model) {
        return "shareProject";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/create-layout", method = RequestMethod.GET)
    public String createProject(Model model) {
        Map<String, String> project = new LinkedHashMap<>();
        project.put(ProjectTypes.DATA_VISUALIZATION.getId().toString(), ProjectTypes.DATA_VISUALIZATION.toString());
        project.put(ProjectTypes.HEALTH_MONITORING.getId().toString(), ProjectTypes.HEALTH_MONITORING.toString());
        model.addAttribute("projectTypes", project);
        return "projectCreation";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String create(@RequestParam("type") String type,
                         @RequestParam("name") String name,
                         @RequestParam("description") String description, Model model) {
        return ProjectTypes.DATA_VISUALIZATION.getId().toString().equals(type) ? "dataVisualizationProjectInitialSetup" : "healthMonitorProjectInitialSetup";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/visualization-setup", method = RequestMethod.GET)
    public String visualizationProjectSetup(Model model) {
        return "dataVisualizationProjectInitialSetup";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/visualization-settings", method = RequestMethod.GET)
    public String visualizationProjectSettings(Model model) {
        return "dataVisualizationAdvancedSettings";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/health-monitor-setup", method = RequestMethod.GET)
    public String healthMonitorSetup(Model model) {
        return "healthMonitorProjectInitialSetup";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/health-monitor-settings", method = RequestMethod.GET)
    public String healthMonitorSettings(Model model) {
        return "healthMonitorAdvancedSettings";
    }


    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/save", method = RequestMethod.GET)
    @ResponseBody
    public Project saveProject(@RequestParam("name") String name,
                               @RequestParam("description") String description,
                               Model model) {
        // return projectDAO.saveProject();
        return null;
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/layout", method = RequestMethod.GET)
    public String projectView(Model model) {
        return "project";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteProject(Project project,
                                 Model model) {
        return projectDAO.deleteProject(project);
    }

}
