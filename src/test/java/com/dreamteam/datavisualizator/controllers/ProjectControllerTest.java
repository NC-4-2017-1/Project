package com.dreamteam.datavisualizator.controllers;


import com.dreamteam.datavisualizator.common.beans.SessionScopeBean;
import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.CreateProjectRequest;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private DataVisualizationProjectDAO dataVisualizationDaoMock;

    @Mock
    private HealthMonitorProjectDAO healthMonitorDaoMock;


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


    @Test
    public void uploadFileForDV_isNotOk() throws Exception {
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

        //!TODO fix this because I don't know what's wrong
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/project/upload")
                .file(csvFile)
                .param("dateFormat", "13")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }



}

