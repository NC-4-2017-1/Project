package com.dreamteam.datavisualizator.controllers;


import com.dreamteam.datavisualizator.common.beans.CustomerProject;
import com.dreamteam.datavisualizator.common.beans.SessionScopeBean;
import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.*;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.GraphicDVImpl;
import com.dreamteam.datavisualizator.models.impl.HealthMonitorProject;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
import com.dreamteam.datavisualizator.services.csvparser.CsvParser;
import com.dreamteam.datavisualizator.services.csvparser.CsvParserImpl;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class})

public class ProjectControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @InjectMocks
    private ProjectController controller;

    @InjectMocks
    private SessionScopeBean sessionScopeBean;

    @Mock
    private UserDAO userDaoMock;

    @Mock
    private DataVisualizationProjectDAO dataVisualizationProjectDAO;

    @Mock
    private HealthMonitorProjectDAO healthMonitorDaoMock;

    @Spy
    private CsvParser csvParser = new CsvParserImpl();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        Class<ProjectController> classPC = ProjectController.class;
        Field field = null;
        try {
            field = classPC.getDeclaredField("sessionScopeBean");
            field.setAccessible(true);
            field.set(controller, sessionScopeBean);
        } catch (Exception e) {
        }
    }


    @Test
    public void tryToCreateDataVisualizationAndHealthMonitorProject_isOk() throws Exception {
        CreateProjectRequest request = new CreateProjectRequest();
        request.setName("Project Test Name");
        request.setDescription("Project Test Description");
        request.setType("3");
        Gson gson = new Gson();
        String json = gson.toJson(request);

        mockMvc.perform(
                post("/project/create").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());


        request.setType("4");
        json = gson.toJson(request);
        mockMvc.perform(
                post("/project/create").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }


    private MockMultipartFile getMockCsvFile() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("testdocuments/svt_sample.csv");
        File file = new File(url.getPath());

        String name = "file";
        String originalFileName = "svt_sample.csv";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
        }
        MockMultipartFile csvFile = new MockMultipartFile(name, originalFileName, contentType, content);
        return csvFile;
    }

    private MockMultipartFile getMockXmlFile() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("testdocuments/test_xml_document.xml");
        File file = new File(url.getPath());

        String name = "file";
        String originalFileName = "test_xml_document.xml";
        String contentType = "text/xml";
        byte[] content = null;
        try {
            content = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
        }
        MockMultipartFile xmlFile = new MockMultipartFile(name, originalFileName, contentType, content);
        return xmlFile;
    }


    @Test
    public void uploadCsvFileForDV_isOkRedirection() throws Exception {
        MockMultipartFile csvFile = getMockCsvFile();

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/project/upload")
                .file(csvFile)
                .param("dateFormat", "13"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/visualization-settings"));
    }

    @Ignore
    @Test
    public void uploadXmlFileForDV_isOkRedirection() throws Exception {
        MockMultipartFile xmlFile = getMockXmlFile();

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/project/upload")
                .file(xmlFile)
                .param("dateFormat", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/visualization-settings"));
        //!TODO Don't know why, but xml parser is not getting autowired
    }


    @Test
    public void uploadCsvFileForDV_isErrorAndRedirectBack_withWrongDateFormat() throws Exception {
        MockMultipartFile csvFile = getMockCsvFile();

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/project/upload")
                .file(csvFile)
                .param("dateFormat", "600"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/visualization-setup"));
    }


    @Test
    public void uploadXmlFileForDV_isErrorAndRedirectBack_withWrongDateFormat() throws Exception {
        MockMultipartFile xmlFile = getMockXmlFile();

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/project/upload")
                .file(xmlFile)
                .param("dateFormat", "322"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/visualization-setup"));
    }


    @Ignore
    @Test
    public void createDVProjectInDB_isOk() throws Exception {
        DataVisualizationGraphicCreationRequest request = new DataVisualizationGraphicCreationRequest();
        URL url = Thread.currentThread().getContextClassLoader().getResource("testdocuments/svt_sample.csv");
        File file = new File(url.getPath());
        sessionScopeBean.getCustomerProject().setFileType("csv");
        sessionScopeBean.getCustomerProject().setFile(file);
        String[] xArr = new String[]{"PROCESS_COUNT"};
        String[] yArr = new String[]{"DB_LOAD"};
        String[] math = new String[]{"DB_LOAD"};
        request.setyAxis(xArr);
        request.setxAxis(yArr);
        request.setmathCol(math);
        String json = new Gson().toJson(request);
        sessionScopeBean.getCustomerProject().setName("projectName");
        sessionScopeBean.getCustomerProject().setDescription("projectDescription");
        sessionScopeBean.getCustomerProject().setDateFormat(DateFormat.getDateFormatById(new BigInteger("13")));
        BigInteger id = BigInteger.valueOf(1L);
        String email = "test@email";
        String firstName = "firstName";
        String lastName = "lastName";
        UserTypes type = UserTypes.REGULAR_USER;

        Project resultProject = Mockito.mock(DataVisualizationProject.class);

        when(dataVisualizationProjectDAO.saveProject(Mockito.any(DataVisualizationProject.class)))
                .thenReturn(resultProject);

        User user = new UserImpl.Builder(email, null)
                .buildId(id)
                .buildFirstName(firstName)
                .buildLastName(lastName)
                .buildType(type)
                .buildUser();
        sessionScopeBean.setUser(user);

        mockMvc.perform(post("/project/save-visualization")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
              //  .andExpect(content().string("successful"));

        List<Graphic> graph = sessionScopeBean.getCustomerProject().getGraphics();


        Assert.assertEquals(1, graph.size());
//      Assert.assertEquals(new BigInteger("1"), actual.get(0).getId());
        Assert.assertEquals("Graph #1 - X axis: 'DB_LOAD'; Y axis: 'PROCESS_COUNT'", graph.get(0).getName());
        Assert.assertTrue(graph.get(0) instanceof GraphicDVImpl);
        GraphicDVImpl graphicDV = (GraphicDVImpl) graph.get(0);
        Assert.assertEquals(new BigDecimal("52.5"), graphicDV.getAverage());
        Assert.assertEquals(new BigDecimal("4512.5"), graphicDV.getDispersion());
        Assert.assertEquals(new BigDecimal("52.5"), graphicDV.getMathExpectation());
        Assert.assertEquals(new BigDecimal("52.5"), graphicDV.getOlympicAverage());

        ArgumentCaptor<Project> argumentCaptor = ArgumentCaptor.forClass(Project.class);
        Mockito.verify(dataVisualizationProjectDAO)
                .saveProject(argumentCaptor.capture());

        Project actualProject = argumentCaptor.getValue();
        Assert.assertEquals("projectName", actualProject.getName());
        Assert.assertEquals("projectDescription", actualProject.getDescription());
        Assert.assertEquals(new BigInteger("1"), actualProject.getAuthor());
        Assert.assertEquals("firstName lastName", actualProject.getAuthorFullName());
        Assert.assertEquals(resultProject, sessionScopeBean.getCustomerProject().getSavedProject());
    }

    @Test
    public void deleteProject() throws Exception{
        BigInteger id = BigInteger.valueOf(1L);
        String email = "test@email";
        String firstName = "firstName";
        String lastName = "lastName";
        String description = "description";
        UserTypes type = UserTypes.REGULAR_USER;


        User user = new UserImpl.Builder(email, null)
                .buildId(id)
                .buildFirstName(firstName)
                .buildLastName(lastName)
                .buildType(type)
                .buildUser();

        String nameDV = "projectDV";
        String nameHM = "projectHM";
        Project projectDV = new DataVisualizationProject.Builder(nameDV, null, id, user.getFullName()).buildProject();
        Project projectHM = new HealthMonitorProject.Builder(id, nameHM, null, description, user.getId(), user.getFullName(), null, null, null, null, null ).buildProject();

        when(dataVisualizationProjectDAO.getProjectById(id)).thenReturn(projectDV);
        when(dataVisualizationProjectDAO.deleteProject(projectDV)).thenReturn(true);
        when(healthMonitorDaoMock.getProjectById(id)).thenReturn(projectHM);
        when(healthMonitorDaoMock.deleteProject(projectHM)).thenReturn(true);

        mockMvc.perform(get("/project/delete")
                .param("id", id.toString())
                .param("project_type", ProjectTypes.DATA_VISUALIZATION.getId().toString()))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/project/delete")
                .param("id", id.toString())
                .param("project_type", ProjectTypes.HEALTH_MONITORING.getId().toString()))
                .andExpect(status().is3xxRedirection());

        verify(dataVisualizationProjectDAO, times(1)).getProjectById(id);
        verify(dataVisualizationProjectDAO, times(1)).deleteProject(projectDV);
        verifyNoMoreInteractions(dataVisualizationProjectDAO);

        verify(healthMonitorDaoMock, times(1)).getProjectById(id);
        verify(healthMonitorDaoMock, times(1)).deleteProject(projectHM);
        verifyNoMoreInteractions(healthMonitorDaoMock);
    }

    @Test
    public void healthMonitorConnectionTest_allTypesOfParameters() throws Exception {
        DataSourceRequest request = new DataSourceRequest();
        request.setServerName("94.158.152.89");
        request.setPort("8080");
        request.setSid("ORCL");
        request.setUsername(System.getenv("TEST_SQL_LOGIN"));
        request.setPassword(System.getenv("TEST_SQL_PASSWORD"));

        Gson gson = new Gson();
        String json = gson.toJson(request);

        mockMvc.perform(
                post("/project/health-monitor-setup-test-conn").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().string("successful"));

        request.setPort("90000");
        json = gson.toJson(request);
        mockMvc.perform(
                post("/project/health-monitor-setup-test-conn").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().string("Port value must be between 0 and000 65000."));


        request.setPort("port");
        json = gson.toJson(request);
        mockMvc.perform(
                post("/project/health-monitor-setup-test-conn").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().string("Enter number for port."));

        request.setPort(null);
        request.setServerName(null);
        request.setSid(null);
        json = gson.toJson(request);
        mockMvc.perform(
                post("/project/health-monitor-setup-test-conn").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(content().string("Connection fields can not be empty!"));
    }

    @Test
    public void healthMonitorSettingsPostTest_returnsOk_allValidParams() throws Exception {
        String tableIndexLobSize = "user";
        String activeSessionsTop = "10";
        String activeQueriesTop = "10";
       // String queriesResultsTop = "10";
        //String queriesMonitorTop = "10";
        //String activeJobsPastHours = "24";
        String graphHourcount = "24";
        String[] selectors = new String[]{""};

        CustomerProject customerProject = sessionScopeBean.getCustomerProject();
        customerProject.setSid("ORCL");
        customerProject.setPort("8080");
        customerProject.setServerName("94.158.152.89");
        customerProject.setPassword(System.getenv("TEST_SQL_PASSWORD"));
        customerProject.setUserName(System.getenv("TEST_SQL_LOGIN"));

        customerProject.setDescription("Description");
        customerProject.setAuthor(BigInteger.valueOf(1));
        customerProject.setName("Test");

        User user = new UserImpl.Builder("email", null).buildFirstName("first").buildLastName("last").buildUser();
        sessionScopeBean.setUser(user);

        mockMvc.perform(
                post("/project/health-monitor-settings-post")
                        .requestAttr("tableindexlob", tableIndexLobSize)
                        .requestAttr("activesession", activeSessionsTop)
                        .requestAttr("activequeries", activeQueriesTop)
                        //.requestAttr("queriesres", queriesResultsTop)
                        //.requestAttr("sqlmonitor", queriesMonitorTop)
                       // .requestAttr("activejobs", activeJobsPastHours)
                        .requestAttr("graph", graphHourcount)
                        .requestAttr("selectors[]", selectors))
                .andExpect(status().isOk());
    }

}

