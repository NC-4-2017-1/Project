package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.common.IdList;
import com.dreamteam.datavisualizator.common.beans.SessionScopeBean;
import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@Secured("ROLE_REGULAR_USER")
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    DataVisualizationProjectDAO projectDAO;

    @Autowired
    HealthMonitorProjectDAO healthMonitorProjectDAOImpl;

    @Autowired
    SessionScopeBean sessionScopeBean;

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/new-layout", method = RequestMethod.GET)
    public String createProject(Model model) {
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
        //sessionScopeBean.getCustomerProject().setType(ProjectTypes.valueOf(type));
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

  /*  @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/uploadStatus";
    }*/


    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/health-monitor-setup", method = RequestMethod.GET)
    public String healthMonitorSetup(
            @RequestParam(value = "isValid", required = false) Boolean isValid,
            @RequestParam(value = "serverName", required = false) String serverName,
            @RequestParam(value = "port", required = false) String port,
            @RequestParam(value = "sid", required = false) String sid,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            Model model) {
        model.addAttribute("isValid", isValid);
        model.addAttribute("serverName", serverName);
        model.addAttribute("port", port);
        model.addAttribute("sid", sid);
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        return "healthMonitorSetup";
    }

    /*@Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/health-monitor-setup-connection", method = RequestMethod.GET)
    public String healthMonitorSetupSave(
            @RequestParam("serverName") String serverName,
            @RequestParam("port") String port,
            @RequestParam("sid") String sid,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        if (healthMonitorProjectDAOImpl.isValidDataSource(serverName, port, sid, username, password)){
            sessionScopeBean.getCustomerProject().setServerName(serverName);
            sessionScopeBean.getCustomerProject().setPort(port);
            sessionScopeBean.getCustomerProject().setSid(sid);
            sessionScopeBean.getCustomerProject().setUserName(username);
            sessionScopeBean.getCustomerProject().setPassword(password);
            return "redirect:health-monitor-settings";
        }
        return UriComponentsBuilder.fromUriString("redirect:health-monitor-setup")
                .queryParam("isValid", false)
                .queryParam("serverName", serverName)
                .queryParam("port", port)
                .queryParam("sid", sid)
                .queryParam("username", username)
                .queryParam("sid", password)
                .toUriString();
    }*/

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
