package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.common.IdList;
import com.dreamteam.datavisualizator.common.beans.SessionScopeBean;
import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.models.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/project")
public class ProjectController {

    public static final Logger LOGGER = Logger.getLogger(ProjectController.class);
    @Autowired
    DataVisualizationProjectDAO projectDAO;

    @Autowired
    HealthMonitorProjectDAO healthMonitorProjectDAOImpl;

    @Autowired
    SessionScopeBean sessionScopeBean;

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/new-layout", method = RequestMethod.GET)
    public String createProject(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userObject");
        sessionScopeBean.setUser(user);
        Map<String, String> project = new LinkedHashMap<>();
        project.put(ProjectTypes.DATA_VISUALIZATION.getId().toString(), ProjectTypes.DATA_VISUALIZATION.toString());
        project.put(ProjectTypes.HEALTH_MONITORING.getId().toString(), ProjectTypes.HEALTH_MONITORING.toString());
        model.addAttribute("projectTypes", project);
        return "newLayout";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String create(@RequestParam("type") String type,
                         @RequestParam("name") String name,
                         @RequestParam("description") String description, Model model) {
        sessionScopeBean.getCustomerProject().setType(ProjectTypes.getRoleById(new BigInteger(type)));
        sessionScopeBean.getCustomerProject().setName(name);
        sessionScopeBean.getCustomerProject().setDescription(description);
        return "redirect:" + (ProjectTypes.DATA_VISUALIZATION.getId().toString().equals(type)
                ? "visualization-setup"
                : "health-monitor-setup");
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/visualization-setup", method = RequestMethod.GET)
    public String visualizationProjectSetup(Model model) {
        return "visualizationSetup";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/visualization-settings", method = RequestMethod.GET)
    public String visualizationProjectSettings(Model model) {
        return "visualizationSettings";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            LOGGER.warn("File '" + file.getOriginalFilename() + "' is empty");
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/project/visualization-setup";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(System.getProperty("java.io.tmpdir")).resolve(file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            LOGGER.error("Uploaded temporary file '" + file.getOriginalFilename() + "' has not be retained", e);
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/project/visualization-setup";
        }
        return "redirect:/project/visualization-settings";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/health-monitor-setup", method = RequestMethod.GET)
    public String healthMonitorSetup(Model model) {
        return "healthMonitorSetup";
    }

    @Secured("ROLE_REGULAR_USER")
    @PostMapping("/health-monitor-setup-test-conn")
    @ResponseBody
    public String healthMonitorSetupTestConn(@RequestBody DataSourceRequest request, Model model) {
        try {
            healthMonitorProjectDAOImpl.setDataSourceTemplate(request.getServerName(),
                    request.getPort(), request.getSid(), request.getUsername(), request.getPassword());
            return "successful";
        } catch (Exception e) {
            LOGGER.error("healthMonitorSetupTestConn" + e.getMessage());
            return e.getMessage();
        }
    }

    @Secured("ROLE_REGULAR_USER")
    @PostMapping("/health-monitor-setup-save")
    @ResponseBody
    public String healthMonitorSetupNext(@RequestBody DataSourceRequest request, Model model) {
        try {
            healthMonitorProjectDAOImpl.setDataSourceTemplate(request.getServerName(),
                    request.getPort(), request.getSid(), request.getUsername(), request.getPassword());
            sessionScopeBean.getCustomerProject().setServerName(request.getServerName());
            sessionScopeBean.getCustomerProject().setPort(request.getPort());
            sessionScopeBean.getCustomerProject().setSid(request.getSid());
            sessionScopeBean.getCustomerProject().setUserName(request.getUsername());
            sessionScopeBean.getCustomerProject().setPassword(request.getPassword());
            return "successful";
        } catch (Exception e) {
            LOGGER.error("healthMonitorSetupNext" + e.getMessage());
            return e.getMessage();
        }
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/health-monitor-settings", method = RequestMethod.GET)
    public String healthMonitorSettings(Model model) {
        return "healthMonitorSettings";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/save-visualization", method = RequestMethod.GET)
    @ResponseBody
    public Project saveVisualizationProject(@RequestParam("name") String name,
                                            @RequestParam("description") String description,
                                            @RequestParam("graphics") List<Graphic> graphics,
                                            User user,
                                            Model model) {

        name = sessionScopeBean.getCustomerProject().getName();
        description = sessionScopeBean.getCustomerProject().getDescription();
        return projectDAO.saveProject(name, user.getId(), description, graphics);
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/save-health-monitor", method = RequestMethod.GET)
    @ResponseBody
    public Project saveHealthMonitorProject(Project project,
                                            Model model) {
        return healthMonitorProjectDAOImpl.saveProject(project);
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteProject(Project project,
                                 Model model) {
        if (project != null) {
            ProjectTypes projectTypes = project.getType();
            if (projectTypes != null && projectTypes.equals(IdList.DATA_VISUALIZATION_PROJECT_OBJTYPE_ID)) {
                return projectDAO.deleteProject(project);
            } else if (projectTypes != null && projectTypes.equals(IdList.HEALTH_MONITOR_PROJECT_OBJTYPE_ID)) {
                return healthMonitorProjectDAOImpl.deleteProject(project);
            }
        }
        return false;
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/layout", method = RequestMethod.GET)
    public String projectView(Model model) {
        return "project";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/share", method = RequestMethod.GET)
    public String shareProject(Model model) {
        return "shareProject";
    }

}
