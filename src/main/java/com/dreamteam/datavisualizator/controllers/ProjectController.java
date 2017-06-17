package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.common.beans.CustomerProject;
import com.dreamteam.datavisualizator.common.beans.SessionScopeBean;
import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.common.exceptions.HMConnectionException;
import com.dreamteam.datavisualizator.common.exceptions.HMGraphException;
import com.dreamteam.datavisualizator.common.exceptions.SelectorCreateException;
import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.common.selectors.impl.*;
import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.*;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.GraphicDVImpl;
import com.dreamteam.datavisualizator.models.impl.HealthMonitorProject;
import com.dreamteam.datavisualizator.services.CalculationService;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import com.dreamteam.datavisualizator.services.JsonSerializer;
import com.dreamteam.datavisualizator.services.csvparser.CsvParser;
import com.dreamteam.datavisualizator.services.xmlparser.XmlParser;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.StringUtils;
import org.jsoup.Jsoup;
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
        sessionScopeBean.getCustomerProject().setName(Jsoup.parse(request.getName()).text());
        sessionScopeBean.getCustomerProject().setDescription(Jsoup.parse(request.getDescription()).text());
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
            redirectAttributes.addFlashAttribute("messageFile", "Please select a file to upload");
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
                redirectAttributes.addFlashAttribute("messageFormat", "Please select a correct FormatDate");
                return "redirect:/project/visualization-setup";
            }
        } catch (IOException e) {
            LOGGER.error("Uploaded temporary file '" + file.getOriginalFilename() + "' has not be retained", e);
            redirectAttributes.addFlashAttribute("messageFile", "Please select a file to upload");
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
                result = csvParser.parseCsvFile(file, sessionScopeBean.getCustomerProject().getDateFormat(), 5);
            } catch (IOException e) {
                LOGGER.error("IOException in parsing proj as csv", e);
            }
        } else if (sessionScopeBean.getCustomerProject().getFileType().equals("xml")) {
            try {
                File file = sessionScopeBean.getCustomerProject().getFile();
                result = xmlParser.parseXmlFile(file, sessionScopeBean.getCustomerProject().getDateFormat(), 5);
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
    @ResponseBody
    @RequestMapping(path = "/save-visualization", method = RequestMethod.POST)
    public String saveVisualizationProject(@RequestBody DataVisualizationGraphicCreationRequest dvGraphicCreationRequest,
                                           Model model) {

        LOGGER.info("Got x axis from client: " + Arrays.toString(dvGraphicCreationRequest.getxAxis()));
        LOGGER.info("Got y axis from client: " + Arrays.toString(dvGraphicCreationRequest.getyAxis()));
        LOGGER.info("Got columns for math from client: " + Arrays.toString(dvGraphicCreationRequest.getmathCol()));

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
            JsonObject jsonObj = JsonSerializer.serializeGraph(result, dvGraphicCreationRequest.getxAxis()[i], dvGraphicCreationRequest.getyAxis()[i], dvGraphicCreationRequest.getmathCol()[i]);
            Graphic graphic = new GraphicDVImpl.DVGraphBuilder()
                    .buildName("Data Visualization graph: " + sessionScopeBean.getCustomerProject().getName() + " " + graphicList.size() + 2)
                    .buildGraphicJSON(jsonObj)
                    .buildAverage(CalculationService.calculateAverage(result, dvGraphicCreationRequest.getmathCol()[i]))
                    .buildDispersion(CalculationService.calculateDispersion(result, dvGraphicCreationRequest.getmathCol()[i]))
                    .buildMathExpectation(CalculationService.calculationMathExpectation(result, dvGraphicCreationRequest.getmathCol()[i]))
                    .buildOlympicAverage(CalculationService.calculateOlympicAverage(result, dvGraphicCreationRequest.getmathCol()[i]))
                    .buildGraphic();
            LOGGER.info(graphic + " added");
            graphicList.add(graphic);
        }
        sessionScopeBean.getCustomerProject().setGraphics(graphicList);


        CustomerProject customerProject = sessionScopeBean.getCustomerProject();
        Project project = new DataVisualizationProject
                .Builder(customerProject.getName(), null, sessionScopeBean.getUser().getId(),sessionScopeBean.getUser().getFullName())
                .buildDescription(customerProject.getDescription())
                .buildGraphics(customerProject.getGraphics())
                .buildProject();
        Project projectFromDb = projectDAO.saveProject(project);
        LOGGER.info(project.getName() + " " + project.getDescription() + " " + project.getAuthor() + " " + project.getType() + " passed further");
        sessionScopeBean.getCustomerProject().setSavedProject((DataVisualizationProject) projectFromDb);

        return "successful";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/project-dv", method = RequestMethod.GET)
    public String projectView(Model model, @RequestParam(value = "projDvId", required = false) BigInteger id) {
        DataVisualizationProject projectToShow = null;

        if (id != null) {
            projectToShow = (DataVisualizationProject) projectDAO.getProjectById(id);
        } else {
            projectToShow = sessionScopeBean.getCustomerProject().getSavedProject();
        }

        if (projectToShow == null) {
            LOGGER.error("Error in printing out project. Project we got from session: " + sessionScopeBean.getCustomerProject().getSavedProject()
                    + "\nId we got from request: " + id);
            return "index";
        }

        LOGGER.info(projectToShow + " is what we got.");
        model.addAttribute("project", projectToShow);

        User author = userDAO.getUserById(projectToShow.getAuthor());
        model.addAttribute("author", author);

        //add to model dv graphics
        List<Graphic> graphics = projectToShow.getGraphics();
        List<GraphicDVImpl> graphicsDV = new ArrayList<>(graphics.size());
        for (Graphic graphic : graphics) {
            graphicsDV.add((GraphicDVImpl) graphic);
        }
        model.addAttribute("graphics", graphicsDV);


        return "projectDV";
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
            String serverName = request.getServerName();
            String port = request.getPort();
            String sid = request.getSid();
            String userName = request.getUsername();
            String password = request.getPassword();
            if(serverName != null && port != null && sid != null && userName != null && password != null) {
                int testPort = Integer.parseInt(port.trim());
                if (testPort >= 1 && testPort <= 65000) {
                    healthMonitorProjectDAOImpl.setDataSourceTemplate(serverName, port, sid, userName, password);
                    return "successful";
                }
                return "Port value must be between 0 and000 65000.";
            }
            return "Connection fields can not be empty!";
        } catch (NumberFormatException e) {
            return "Enter number for port.";
        } catch (HMConnectionException e) {
            LOGGER.error("Hm connection error." + e.getMessage());
            return e.getMessage();
        }
    }

    @Secured("ROLE_REGULAR_USER")
    @PostMapping("/health-monitor-setup-save")
    @ResponseBody
    public String healthMonitorSetupNext(@RequestBody DataSourceRequest request, Model model) {
        try {
            String serverName = request.getServerName();
            String port = request.getPort();
            String sid = request.getSid();
            String userName = request.getUsername();
            String password = request.getPassword();
            if(serverName != null && port != null && sid != null && userName != null && password != null) {
                int testPort = Integer.parseInt(port.trim());
                if (testPort >= 1 && testPort <= 65000) {
                    healthMonitorProjectDAOImpl.setDataSourceTemplate(serverName, port, sid, userName, password);
                    sessionScopeBean.getCustomerProject().setServerName(request.getServerName());
                    sessionScopeBean.getCustomerProject().setPort(request.getPort());
                    sessionScopeBean.getCustomerProject().setSid(request.getSid());
                    sessionScopeBean.getCustomerProject().setUserName(request.getUsername());
                    sessionScopeBean.getCustomerProject().setPassword(request.getPassword());
                    return "successful";
                }
                return "Port value must be between 0 and 65000.";
            }
            return "Connection fields can not be empty!";
        } catch (NumberFormatException e) {
            return "Enter number for port";
        } catch (HMConnectionException e) {
            LOGGER.error("Hm connection error" + e.getMessage());
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
                                            @RequestParam(value = "activejobs", defaultValue = "24") String activeJobsPastHours,
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
                null, customerProject.getDescription(), customerProject.getAuthor(), sessionScopeBean.getUser().getFullName(),
                customerProject.getSid(),
                customerProject.getPort(), customerProject.getServerName(), customerProject.getUserName(),
                customerProject.getPassword());
        projectBuilder.buildProject();
        try {
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
                            LOGGER.error("HM DAO return null graphic after create");
                            model.addAttribute("errorGraphic", "Graph not created for project.");
                        }
                    } catch (Exception e) {
                        LOGGER.error("HM DAO return null graphic after create", e);
                        throw new HMGraphException("Graph not created from HM data base. " + e.getLocalizedMessage());
                    }
                }
            }
            Map<BigInteger, Selector> mapSelectors = healthMonitorProjectDAOImpl.createSelectorList(selectorsType);
            projectBuilder.buildSelectors(mapSelectors);
            Project p = projectBuilder.buildProject();
            Project projectNew = healthMonitorProjectDAOImpl.saveProject(p);
            if (projectNew == null) {
                LOGGER.error("HM DAO return null project after saving");
                model.addAttribute("errorProject", "Project not created and saved!!!");
            }
            if (model.containsAttribute("errorGraphic") || model.containsAttribute("errorSelector") || model.containsAttribute("errorProject")) {
                return "healthMonitorSettings";
            }
            customerProject.setIdProject(projectNew.getId());
        } catch (SelectorCreateException e) {
            LOGGER.error("Selectors not created", e);
            model.addAttribute("errorSelector", "<strong>Selectors not created for project.</strong> " + e.getLocalizedMessage());
            return "healthMonitorSettings";
        } catch (HMGraphException e) {
            LOGGER.error("Graph not created from HM data base. ", e);
            model.addAttribute("errorGraphic", "<strong>Graph not created for project.</strong>" + e.getLocalizedMessage());
            return "healthMonitorSettings";
        }
        return "redirect:/project/project-hm";
    }


    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/project-hm", method = RequestMethod.GET)
    public String openHealthMonitorProject(Model model, @RequestParam(value = "projHmId", required = false) BigInteger id) {
        try {
            BigInteger finalProjId = null;
            Project project = null;
            if (id != null) {
                finalProjId = id;
            } else {
                finalProjId = sessionScopeBean.getCustomerProject().getIdProject();
            }

            if (finalProjId == null) {
                LOGGER.error("Error in printing out project. Project we got from session: " + sessionScopeBean.getCustomerProject().getSavedProject()
                        + "\nId we got from request: " + id);
                return "index";
            }

            project = healthMonitorProjectDAOImpl.getProjectById(finalProjId);

            Map<BigInteger, SelectorCreator> mapSelectorCreators = healthMonitorProjectDAOImpl.getSelectorCreators();
            Map<BigInteger, String> mapSelectorAttr = new HashMap<>();
            model.addAttribute("project", project);
            Map<BigInteger, Selector> projectSelectors = ((HealthMonitorProject) project).getSelectors();
            for (Map.Entry<BigInteger, Selector> entry : projectSelectors.entrySet()) {
                BigInteger key = entry.getKey();
                Selector selector = entry.getValue();
                mapSelectorAttr.put(key, mapSelectorCreators.get(key).getValueForShow(selector));
            }
            model.addAttribute("attrSelectors", mapSelectorAttr);

            User author = userDAO.getUserById(project.getAuthor());
            model.addAttribute("author", author);
            return "projectHM";
        } catch (SelectorCreateException e) {
            LOGGER.error("Selectors not fetched for project. ", e);
            model.addAttribute("errorSelector", "<strong>Selectors error.</strong> " + e.getLocalizedMessage());
            return "projectHM";
        } catch (HMGraphException e) {
            LOGGER.error("Graph not selected. ", e);
            model.addAttribute("errorGraphic", "<strong>Graphic error.</strong> " + e.getLocalizedMessage());
            return "projectHM";
        } catch (Exception e) {
            LOGGER.error("Project open error. ", e);
            model.addAttribute("errorProject", "<strong>Project open error.</strong> " + e.getLocalizedMessage());
            return "projectHM";
        }
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/delete/{id}/{project_type}", method = RequestMethod.GET)
    public String deleteProject(@PathVariable BigInteger id,@PathVariable ProjectTypes project_type) {
        if (project_type != null && project_type.equals(ProjectTypes.DATA_VISUALIZATION)) {
            Project project = projectDAO.getProjectById(id);
            //LOGGER.info("Project DV we got " + project);
            if (project != null) {
                projectDAO.deleteProject(project);
            }
        } else if (project_type != null && project_type.equals(ProjectTypes.HEALTH_MONITORING)) {
            Project project = healthMonitorProjectDAOImpl.getProjectById(id);
            //LOGGER.info("Project HM we got " + project);
            if (project != null) {
                healthMonitorProjectDAOImpl.deleteProject(project);
            }
        }
        //TODO show errors
        return "redirect:/user/dashboard-get/4/desc/1";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/share/{idProject}/{field}/{sortType}", method = RequestMethod.GET)
    public String shareProject(Model model, @PathVariable String idProject,
                               @PathVariable int field,
                               @PathVariable String sortType, HttpServletRequest request
    ) {
        if(!"desc".equals(sortType) && !"asc".equals(sortType)) {
            sortType = "asc";
        }
        if (field != 2 && field != 3 && field != 4){
            field = 3;
        }
        try {
            Integer idP = Integer.parseInt(idProject.trim());
            model.addAttribute("sortF", field);
            model.addAttribute("sortT", sortType);
            List<User> users = userDAO.getAllUsersList(field, sortType);
            BigInteger userID = ((User) request.getSession().getAttribute("userObject")).getId();
            Iterator<User> iter = users.iterator();
            while (iter.hasNext()) {
                User author = iter.next();
                if (userID.equals(author.getId())) {
                    iter.remove();
                }
            }
            model.addAttribute("users", users);
            model.addAttribute("project_id", idProject);
            List<User> users_with_access = userDAO.getUsersThatHaveAccessToProject(BigInteger.valueOf(idP));
            model.addAttribute("users_with_access", users_with_access);
            return "shareProject";
        } catch (NumberFormatException e){
            return "redirect:/user/dashboard-get/0/s/0";
        }
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/share/{id}/{user_id}", method = RequestMethod.GET)
    public boolean shareProject(Model model, @PathVariable BigInteger id, @PathVariable BigInteger user_id) {
        if (id != null && user_id != null) {
            return userDAO.giveUserAccessToProject(user_id, id);
        }
        return false;
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/unshare/{id}/{user_id}", method = RequestMethod.GET)
    public boolean UnShareProject(Model model, @PathVariable BigInteger id,
                                @PathVariable BigInteger user_id) {
        if (id != null && user_id != null) {
            return userDAO.removeAccessToProjectFromUser(user_id, id);
        }
        return false;
    }
}
