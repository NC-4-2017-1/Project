package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.common.IdList;
import com.dreamteam.datavisualizator.common.beans.CustomerProject;
import com.dreamteam.datavisualizator.common.beans.SessionScopeBean;
import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.models.*;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.HealthMonitorProject;
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
import java.util.*;

import static com.dreamteam.datavisualizator.common.IdList.*;


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
    @PostMapping("/create")
    @ResponseBody
    public String create(@RequestBody CreateProjectRequest request, Model model) {
        sessionScopeBean.getCustomerProject().setType(ProjectTypes.getRoleById(new BigInteger(request.getType())));
        sessionScopeBean.getCustomerProject().setName(request.getName());
        sessionScopeBean.getCustomerProject().setDescription(request.getDescription());
        if (ProjectTypes.DATA_VISUALIZATION.getId().toString().equals(request.getType())) {
            return "visualization-setup";
        } else {
            return "health-monitor-setup";
        }
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/visualization-setup", method = RequestMethod.GET)
    public String visualizationProjectSetup(Model model) {
        Map<String, String> dateFormat = new LinkedHashMap<>();
        dateFormat.put(DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER.getId().toString(), DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER.toString());
        dateFormat.put(DateFormat.EET_WITH_DOT_DELIMITER.getId().toString(), DateFormat.EET_WITH_DOT_DELIMITER.toString());
        dateFormat.put(DateFormat.EET_WITH_TIME_AND_HYPHEN_DELIMITER.getId().toString(), DateFormat.EET_WITH_TIME_AND_HYPHEN_DELIMITER.toString());
        dateFormat.put(DateFormat.EET_WITH_HYPHEN_DELIMITER.getId().toString(), DateFormat.EET_WITH_HYPHEN_DELIMITER.toString());
        dateFormat.put(DateFormat.EET_WITH_TIME_AND_SLASH_DELIMITER.getId().toString(), DateFormat.EET_WITH_TIME_AND_SLASH_DELIMITER.toString());
        dateFormat.put(DateFormat.EET_WITH_SLASH_DELIMITER.getId().toString(), DateFormat.EET_WITH_SLASH_DELIMITER.toString());
        dateFormat.put(DateFormat.WET_WITH_TIME_AND_DOT_DELIMITER.getId().toString(), DateFormat.WET_WITH_TIME_AND_DOT_DELIMITER.toString());
        dateFormat.put(DateFormat.WET_WITH_DOT_DELIMITER.getId().toString(), DateFormat.WET_WITH_DOT_DELIMITER.toString());
        dateFormat.put(DateFormat.WET_WITH_TIME_AND_HYPHEN_DELIMITER.getId().toString(), DateFormat.WET_WITH_TIME_AND_HYPHEN_DELIMITER.toString());
        dateFormat.put(DateFormat.WET_WITH_HYPHEN_DELIMITER.getId().toString(), DateFormat.WET_WITH_HYPHEN_DELIMITER.toString());
        dateFormat.put(DateFormat.WET_WITH_TIME_AND_SLASH_DELIMITER.getId().toString(), DateFormat.WET_WITH_TIME_AND_SLASH_DELIMITER.toString());
        dateFormat.put(DateFormat.WET_WITH_SLASH_DELIMITER.getId().toString(), DateFormat.WET_WITH_SLASH_DELIMITER.toString());
        dateFormat.put(DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS.getId().toString(), DateFormat.EET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS.toString());
        dateFormat.put(DateFormat.EET_WITH_TIME_AND_HYPHEN_DELIMITER_WITHOUT_SECONDS.getId().toString(),
                DateFormat.EET_WITH_TIME_AND_HYPHEN_DELIMITER_WITHOUT_SECONDS.toString());
        dateFormat.put(DateFormat.EET_WITH_TIME_AND_SLASH_DELIMITER_WITHOUT_SECONDS.getId().toString(),
                DateFormat.EET_WITH_TIME_AND_SLASH_DELIMITER_WITHOUT_SECONDS.toString());
        dateFormat.put(DateFormat.WET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS.getId().toString(),
                DateFormat.WET_WITH_TIME_AND_DOT_DELIMITER_WITHOUT_SECONDS.toString());
        dateFormat.put(DateFormat.WET_WITH_TIME_AND_HYPHEN_DELIMITER_WITHOUT_SECONDS.getId().toString(),
                DateFormat.WET_WITH_TIME_AND_HYPHEN_DELIMITER_WITHOUT_SECONDS.toString());
        dateFormat.put(DateFormat.WET_WITH_TIME_AND_SLASH_DELIMITER_WITHOUT_SECONDS.getId().toString(),
                DateFormat.WET_WITH_TIME_AND_SLASH_DELIMITER_WITHOUT_SECONDS.toString());
        model.addAttribute("dateFormat", dateFormat);
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
    @RequestMapping(path = "/health-monitor-settings-post", method = RequestMethod.POST)
    @ResponseBody
    public String healthMonitorSettingsPost(@RequestParam(value = "tableindexlob", defaultValue = "user") String tableIndexLobSize,
                                            @RequestParam(value = "activesession", defaultValue = "10") String activeSessionsTop,
                                            @RequestParam(value = "activequeries", defaultValue = "10") String activeQueriesTop,
                                            @RequestParam(value = "queriesres", defaultValue = "10") String queriesResultsTop,
                                            @RequestParam(value = "sqlmonitor", defaultValue = "10") String queriesMonitorTop,
                                            @RequestParam(value = "activejobscheck", defaultValue = "24") String activeJobsPastHours,
                                            @RequestParam(value = "graph", defaultValue = "24") int graphHourcount,
                                            @RequestParam("selectors[]") String[] selectors,
                                            Model model
    ) {
        Map<BigInteger, String> selectorsParam = new HashMap<>();
        selectorsParam.put(S_INSTANCE_INFO_OBJTYPE_ID, null);
        selectorsParam.put(S_SIZE_TABLESPACE_OBJTYPE_ID, null);
        selectorsParam.put(S_SIZE_INDEX_LOB_OBJTYPE_ID, tableIndexLobSize);
        selectorsParam.put(S_LAST_ERRORS_OBJTYPE_ID, null);
        selectorsParam.put(S_ACTIVE_SESSIONS_OBJTYPE_ID, activeSessionsTop);
        selectorsParam.put(S_ACTIVE_QUERIES_OBJTYPE_ID, activeQueriesTop);
        selectorsParam.put(S_QUERIES_RESULTS_OBJTYPE_ID, queriesResultsTop);
        selectorsParam.put(S_SQL_MONITOR_OBJTYPE_ID, queriesMonitorTop);
        selectorsParam.put(S_DB_LOCKS_OBJTYPE_ID, null);
        selectorsParam.put(S_ACTIVE_JOBS_OBJTYPE_ID, activeJobsPastHours);

        Map<BigInteger, String> selectorsType = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < selectors.length; i++) {
            if (Integer.parseInt(selectors[i]) != 0) {
                BigInteger key = BigInteger.valueOf(Long.parseLong(selectors[i]));
                selectorsType.put(key, selectorsParam.get(key));
            } else {
                sessionScopeBean.getCustomerProject().setHourCountGraph(graphHourcount);
            }
            //stringBuilder.append(" - ").append(selectors[i]);
        }
        sessionScopeBean.getCustomerProject().setSelectors(selectorsType);
        return "oooo" + graphHourcount;
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/save-visualization", method = RequestMethod.GET)
    @ResponseBody
    public Project saveVisualizationProject(Model model) {
        CustomerProject customerProject = sessionScopeBean.getCustomerProject();
        Project project = new DataVisualizationProject
                .Builder(customerProject.getName(), null, sessionScopeBean.getUser().getId())
                .buildDescription(customerProject.getDescription())
                .buildGraphics(customerProject.getGraphics())
                .buildProject();
        return projectDAO.saveProject(project);
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/save-health-monitor", method = RequestMethod.GET)
    @ResponseBody
    public Project saveHealthMonitorProject(Model model) {
        CustomerProject customerProject = sessionScopeBean.getCustomerProject();
        HealthMonitorProject.Builder projectBuilder = new HealthMonitorProject.Builder(null, customerProject.getName(),
                null, customerProject.getDescription(), customerProject.getAuthor(), customerProject.getSid(),
                customerProject.getPort(), customerProject.getServerName(), customerProject.getUserName(),
                customerProject.getPassword());
        projectBuilder.buildProject();
        //TODO Build Graph
        //TODO Build Selectors
        return healthMonitorProjectDAOImpl.saveProject(projectBuilder.buildProject());
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
        return "projectDV";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/share", method = RequestMethod.GET)
    public String shareProject(Model model) {
        return "shareProject";
    }

}
