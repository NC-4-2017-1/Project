package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class})
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserDAO userDaoMock;

    @InjectMocks
    private UserController controller;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getAdminDashboard_shouldGetAllUsersAndRenderThemToView() throws Exception {

        User user1 = new UserImpl.Builder("test@email1", null)
                .buildId(BigInteger.valueOf(1L))
                .buildFirstName("FirstName1")
                .buildLastName("LastName1")
                .buildType(UserTypes.REGULAR_USER)
                .buildUserHaveAccessToProjects(null)
                .buildUserProjects(null)
                .buildUser();

        User user2 = new UserImpl.Builder("test@email2", null)
                .buildId(BigInteger.valueOf(2L))
                .buildFirstName("FirstName2")
                .buildLastName("LastName2")
                .buildType(UserTypes.REGULAR_USER)
                .buildUserHaveAccessToProjects(null)
                .buildUserProjects(null)
                .buildUser();

        when(userDaoMock.getAllUsersList()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/user/admin-panel"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminDashboard"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(BigInteger.valueOf(1L))),
                                hasProperty("firstName", is("FirstName1")),
                                hasProperty("lastName", is("LastName1")),
                                hasProperty("email", is("test@email1"))
                        )
                )))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(BigInteger.valueOf(2L))),
                                hasProperty("firstName", is("FirstName2")),
                                hasProperty("lastName", is("LastName2")),
                                hasProperty("email", is("test@email2"))
                        )
                )));
        verify(userDaoMock, times(1)).getAllUsersList();
        verifyNoMoreInteractions(userDaoMock);
    }
}
