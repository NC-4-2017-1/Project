package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.common.IdList;
import com.dreamteam.datavisualizator.common.beans.CustomerProject;
import com.dreamteam.datavisualizator.common.beans.SessionScopeBean;
import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.common.exceptions.HMGraphException;
import com.dreamteam.datavisualizator.common.exceptions.SelectorCreateException;
import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.*;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.GraphicDVImpl;
import com.dreamteam.datavisualizator.models.impl.HealthMonitorProject;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import com.dreamteam.datavisualizator.services.JsonSerializer;
import com.dreamteam.datavisualizator.services.csvparser.CsvParser;
import com.dreamteam.datavisualizator.services.xmlparser.XmlParser;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
    UserDAO userDAO;

    @Autowired
    HealthMonitorProjectDAO healthMonitorProjectDAOImpl;

    @Autowired
    SessionScopeBean sessionScopeBean;

    @Autowired
    CsvParser csvParser;

    @Autowired
    XmlParser xmlParser;


    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/new-layout", method = RequestMethod.GET)
    public String createProject(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userObject");
        sessionScopeBean.setUser(user);
        sessionScopeBean.getCustomerProject().setAuthor(user.getId());
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
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("dateFormat") String dateFormat,
                                   @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Model model) {
        if (file.isEmpty()) {
            LOGGER.warn("File '" + file.getOriginalFilename() + "' is empty");
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/project/visualization-setup";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(System.getProperty("java.io.tmpdir")).resolve(file.getOriginalFilename());
            Files.write(path, bytes);
            DateFormat dateFormatById = DateFormat.getDateFormatById(new BigInteger(dateFormat));
            String fileType = file.getOriginalFilename().split("\\.")[1];
            File fileFromTomcat = new File(Paths.get(System.getProperty("java.io.tmpdir")).resolve(file.getOriginalFilename()).toUri());


            boolean checkFormatDate = checkDateFormat(fileFromTomcat, dateFormatById, fileType);
            if (checkFormatDate) {
                sessionScopeBean.getCustomerProject().setDateFormat(dateFormatById);
                sessionScopeBean.getCustomerProject().setFileType(fileType);
                sessionScopeBean.getCustomerProject().setFile(fileFromTomcat);
            } else {
                redirectAttributes.addFlashAttribute("message", "Please select a correct FormatDate");
                model.addAttribute("errorDateFormat", "Please select a correct FormatDate");
                return "redirect:/project/visualization-setup";
            }
        } catch (IOException e) {
            LOGGER.error("Uploaded temporary file '" + file.getOriginalFilename() + "' has not be retained", e);
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/project/visualization-setup";
        }
        return "redirect:/project/visualization-settings";
    }

    private boolean checkDateFormat(File file, DateFormat dateFormat, String fileType) throws IOException {
        if (fileType.equals("csv")) {
            try {
                csvParser.parseCsvFile(file, dateFormat, 10);
                return true;
            } catch (IOException e) {
                LOGGER.error("IOException in parsing proj as csv", e);
            }
        } else if (fileType.equals("xml")) {
            try {
                xmlParser.parseXmlFile(file, dateFormat, 10);
                return true;
            } catch (IOException e) {
                LOGGER.error("IOException in parsing proj as xml", e);
            }
        }
        return false;
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/visualization-settings", method = RequestMethod.GET)
    public String visualizationProjectSettings(Model model) {
        List<Map<String, Object>> result = null;
        if (sessionScopeBean.getCustomerProject().getFileType().equals("csv")) {
            try {
                File file = sessionScopeBean.getCustomerProject().getFile();
                result = csvParser.parseCsvFile(file, sessionScopeBean.getCustomerProject().getDateFormat(), 10);
            } catch (IOException e) {
                LOGGER.error("IOException in parsing proj as csv", e);
            }
        } else if (sessionScopeBean.getCustomerProject().getFileType().equals("xml")) {
            try {
                File file = sessionScopeBean.getCustomerProject().getFile();
                result = xmlParser.parseXmlFile(file, sessionScopeBean.getCustomerProject().getDateFormat(), 10);
            } catch (IOException e) {
                LOGGER.error("IOException in parsing proj as xml", e);
            }
        }

        String tablee = HtmlSerializer.createHtmlTableForParsingFile(result, "dvtable");
        model.addAttribute("table", tablee);
        model.addAttribute("tableKeys", result.get(0).keySet());

        return "visualizationSettings";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/save-visualization", method = RequestMethod.POST)
    public String saveVisualizationProject(@RequestBody DataVisualizationGraphicCreationRequest dvGraphicCreationRequest, RedirectAttributes redirectAttributes,
                                           Model model) {

        List<Map<String, Object>> result = null;
        if (sessionScopeBean.getCustomerProject().getFileType().equals("csv")) {
            try {
                File file = sessionScopeBean.getCustomerProject().getFile();
                result = csvParser.parseCsvFile(file, sessionScopeBean.getCustomerProject().getDateFormat());
            } catch (IOException e) {
                LOGGER.error("IOException in parsing proj as csv", e);
            }
        } else if (sessionScopeBean.getCustomerProject().getFileType().equals("xml")) {
            try {
                File file = sessionScopeBean.getCustomerProject().getFile();
                result = xmlParser.parseXmlFile(file, sessionScopeBean.getCustomerProject().getDateFormat());
            } catch (IOException e) {
                LOGGER.error("IOException in parsing proj as xml", e);
            }
        }

        List<Graphic> graphicList = new ArrayList<>();

        for (int i = 0; i < dvGraphicCreationRequest.getyAxis().length && i < dvGraphicCreationRequest.getxAxis().length; i++) {
            JsonObject jsonObj = JsonSerializer.serializeGraph(result,
                    dvGraphicCreationRequest.getxAxis()[i], dvGraphicCreationRequest.getyAxis()[i]);
            Graphic graphic = new GraphicDVImpl.DVGraphBuilder()
                    .buildName("Data Visualization graph: " + sessionScopeBean.getCustomerProject().getName() + " " + graphicList.size() + 2)
                    .buildGraphicJSON(jsonObj)
                    .buildAverage(new BigDecimal("4.33"))
                    .buildDispersion(new BigDecimal("4.34"))
                    .buildMathExpectation(new BigDecimal("4.35"))
                    .buildOlympicAverage(new BigDecimal("4.36"))
                    .buildGraphic();
            graphicList.add(graphic);
        }
        sessionScopeBean.getCustomerProject().setGraphics(graphicList);


        CustomerProject customerProject = sessionScopeBean.getCustomerProject();
        Project project = new DataVisualizationProject
                .Builder(customerProject.getName(), null, sessionScopeBean.getUser().getId())
                .buildDescription(customerProject.getDescription())
                .buildGraphics(customerProject.getGraphics())
                .buildProject();
     //   Project projectFromDb = projectDAO.saveProject(project);
        redirectAttributes.addFlashAttribute("savedProject", project);

        return "redirect:/project/project-dv";
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
    public String healthMonitorSettingsPost(@RequestParam(value = "tableindexlob", defaultValue = "user") String tableIndexLobSize,
                                            @RequestParam(value = "activesession", defaultValue = "10") String activeSessionsTop,
                                            @RequestParam(value = "activequeries", defaultValue = "10") String activeQueriesTop,
                                            @RequestParam(value = "queriesres", defaultValue = "10") String queriesResultsTop,
                                            @RequestParam(value = "sqlmonitor", defaultValue = "10") String queriesMonitorTop,
                                            @RequestParam(value = "activejobscheck", defaultValue = "24") String activeJobsPastHours,
                                            @RequestParam(value = "graph", defaultValue = "24") int graphHourcount,
                                            @RequestParam(value = "selectors[]", defaultValue = "") String[] selectors,
                                            Model model
    ) {
        Map<BigInteger, String> selectorsParam = new HashMap<>();
        selectorsParam.put(S_SIZE_TABLESPACE_OBJTYPE_ID, null);
        selectorsParam.put(S_SIZE_INDEX_LOB_OBJTYPE_ID, tableIndexLobSize);
        selectorsParam.put(S_LAST_ERRORS_OBJTYPE_ID, null);
        selectorsParam.put(S_ACTIVE_SESSIONS_OBJTYPE_ID, activeSessionsTop);
        selectorsParam.put(S_ACTIVE_QUERIES_OBJTYPE_ID, activeQueriesTop);
        selectorsParam.put(S_QUERIES_RESULTS_OBJTYPE_ID, queriesResultsTop);
        selectorsParam.put(S_SQL_MONITOR_OBJTYPE_ID, queriesMonitorTop);
        selectorsParam.put(S_DB_LOCKS_OBJTYPE_ID, null);
        selectorsParam.put(S_ACTIVE_JOBS_OBJTYPE_ID, activeJobsPastHours);

        CustomerProject customerProject = sessionScopeBean.getCustomerProject();
        HealthMonitorProject.Builder projectBuilder = new HealthMonitorProject.Builder(null, customerProject.getName(),
                null, customerProject.getDescription(), customerProject.getAuthor(), customerProject.getSid(),
                customerProject.getPort(), customerProject.getServerName(), customerProject.getUserName(),
                customerProject.getPassword());
        projectBuilder.buildProject();
        healthMonitorProjectDAOImpl.setDataSourceTemplate(customerProject.getServerName(), customerProject.getPort(), customerProject.getSid(),
                customerProject.getUserName(), customerProject.getPassword());
        Map<BigInteger, String> selectorsType = new HashMap<>();
        selectorsType.put(S_INSTANCE_INFO_OBJTYPE_ID, null);
        for (int i = 0; i < selectors.length; i++) {
            if (Integer.parseInt(selectors[i]) != 0) {
                BigInteger key = BigInteger.valueOf(Long.parseLong(selectors[i]));
                selectorsType.put(key, selectorsParam.get(key));
            } else {
                try {
                    Graphic graph = healthMonitorProjectDAOImpl.createGraph(graphHourcount);
                    if (graph != null) {
                        projectBuilder.buildGraphic(graph);
                    } else {
                        model.addAttribute("errorGraphic", "Graph not created for project.");
                    }
                }catch (Exception e) {
                    LOGGER.error("Graph not created", e);
                    model.addAttribute("errorGraphic", "Graph not created for project."+e.getLocalizedMessage());
                }
            }
        }
        try{
            if (selectorsType != null) {
                Map<BigInteger, Selector> mapSelectors = healthMonitorProjectDAOImpl.createSelectorList(selectorsType);
                projectBuilder.buildSelectors(mapSelectors);
            } else {
                model.addAttribute("errorSelector", "Selectors not created for project.");
            }
        }catch (SelectorCreateException e) {
            LOGGER.error("Selectors not created", e);
            model.addAttribute("errorSelector", "<strong>Selectors not created for project.</strong> "+e.getLocalizedMessage());
        }
        Project p = projectBuilder.buildProject();
        Project projectNew = healthMonitorProjectDAOImpl.saveProject(p);
        if (projectNew == null) {
            model.addAttribute("errorProject", "Project not created!!!");
        }
        if (model.containsAttribute("errorGraphic") || model.containsAttribute("errorSelector") || model.containsAttribute("errorProject")) {
            return "healthMonitorSettings";
        }
        customerProject.setIdProject(projectNew.getId());
        return "redirect:/project/project-hm";
    }


    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/project-hm", method = RequestMethod.GET)
    public String openHealthMonitorProject(Model model, RedirectAttributes redirectAttributes) {
        try{
            BigInteger id = sessionScopeBean.getCustomerProject().getIdProject();
            //Project project = healthMonitorProjectDAOImpl.getProjectById(BigInteger.valueOf(478L));
            Project project = healthMonitorProjectDAOImpl.getProjectById(id);
            if(project != null) {
                model.addAttribute("project", project);
                User author = userDAO.getUserById(project.getAuthor());
                model.addAttribute("author", author);
            } else {
                LOGGER.error("Project open error. ID project - " + id + " is wrong.");
                model.addAttribute("errorProject", "<strong>Project open error.</strong> ID project - " + id + " is wrong.");
            }
        }catch (SelectorCreateException e) {
            LOGGER.error("Selectors not fetched for project. ", e);
            model.addAttribute("errorSelector", "<strong>Selectors error.</strong> "+e.getLocalizedMessage());
        }catch (HMGraphException e){
            LOGGER.error("Graph not selected. ", e);
            model.addAttribute("errorGraphic", "<strong>Graphic error.</strong> " + e.getLocalizedMessage());
        }catch (Exception e) {
            LOGGER.error("Project open error. ", e);
            model.addAttribute("errorProject", "<strong>Project open error.</strong> "+e.getLocalizedMessage());
        }
        /*if (model.containsAttribute("errorGraphic") || model.containsAttribute("errorSelector") || model.containsAttribute("errorProject")) {
            return "projectHM";
        }*/
        return "projectHM";
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
    @RequestMapping(path = "/project-dv", method = RequestMethod.GET)
    public String projectView(Model model, RedirectAttributes redirectAttributes/*, @RequestParam("projDvId") BigInteger id*/) {
        DataVisualizationProject dvProject = (DataVisualizationProject) projectDAO.getProjectById(BigInteger.valueOf(55L));
        Project pr = (Project) model.asMap().get("savedProject");
        LOGGER.info(pr + " !saved!");
        model.addAttribute("project", dvProject);

        User author = userDAO.getUserById(dvProject.getAuthor());
        model.addAttribute("author", author);

        //add to model dv graphics
        List<Graphic> graphics = dvProject.getGraphics();
        List<GraphicDVImpl> graphicsDV = new ArrayList<>(graphics.size());
        for (Graphic graphic : graphics) {
            graphicsDV.add((GraphicDVImpl) graphic);
        }
        model.addAttribute("graphics", graphicsDV);


        return "projectDV";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/share", method = RequestMethod.GET)
    public String shareProject(Model model) {
        return "shareProject";
    }

}
