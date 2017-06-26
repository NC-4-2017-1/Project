package com.dreamteam.datavisualizator.controllers;


import com.dreamteam.datavisualizator.common.beans.SessionScopeBean;
import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.*;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.GraphicDVImpl;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Ignore
    @Test
    public void uploadFileForDV_isOk() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("testdocuments/svt_sample.csv");
        File file = new File(url.getPath());

        String name = "svt_sample.csv";
        String originalFileName = "svt_sample.csv";
        String contentType = "multipart/form-data";
        byte[] content = null;
        try {
            content = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
        }
        MockMultipartFile csvFile = new MockMultipartFile(name,
                originalFileName, contentType, content);

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/project/upload")
                .file(csvFile)
                .param("dateFormat", "13")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


   // @Ignore
    @Test
    public void createDVProjectInDB() throws Exception {
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
        User user = Mockito.mock(User.class);
        sessionScopeBean.setUser(user);


        mockMvc.perform(post("/project/save-visualization")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("successful"));

        List<Graphic> actual = sessionScopeBean.getCustomerProject().getGraphics();

        Assert.assertEquals(1, actual.size());
//      Assert.assertEquals(new BigInteger("1"), actual.get(0).getId());
        Assert.assertEquals("Graph #1 - X axis: 'DB_LOAD'; Y axis: 'PROCESS_COUNT'", actual.get(0).getName());
        Assert.assertTrue(actual.get(0) instanceof GraphicDVImpl);
        GraphicDVImpl graphicDV = (GraphicDVImpl) actual.get(0);
        Assert.assertEquals(new BigDecimal("52.5"), graphicDV.getAverage());
        Assert.assertEquals(new BigDecimal("4512.5"),graphicDV.getDispersion());
        Assert.assertEquals(new BigDecimal("52.5"),graphicDV.getMathExpectation());
        Assert.assertEquals(new BigDecimal("52.5"),graphicDV.getOlympicAverage());

/*        Project project = new DataVisualizationProject.Builder(name, null, authorId, authorFullName)
                .buildDescription(description)
                .buildId(id)
                .buildGraphics(actual)
                .buildProject();*/

        ArgumentCaptor<DataVisualizationProject> argumentCaptor = ArgumentCaptor.forClass(DataVisualizationProject.class);
        Mockito.verify(dataVisualizationProjectDAO)
                .saveProject(argumentCaptor.capture());

        DataVisualizationProject actualProject = argumentCaptor.getValue();
        Assert.assertEquals("projectName",actualProject.getName());
        Assert.assertEquals("projectDescription",actualProject.getDescription());
        Assert.assertEquals(null, actualProject.getAuthor());
    }



}

