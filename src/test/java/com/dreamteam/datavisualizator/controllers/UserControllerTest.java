package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void init() {
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

        when(userDaoMock.getAllUsersList("email", "asc", null)).thenReturn(Arrays.asList(user1, user2));

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
        verify(userDaoMock, times(1)).getAllUsersList("email", "asc", null);
        verifyNoMoreInteractions(userDaoMock);
    }

    @Test
    public void createUser_shouldGetErrorMessageFromSessionAndRenderEmptyModelAttributeOnViewForBinding() throws Exception {

        ResultActions actions = mockMvc.perform(get("/user/create-user").sessionAttr("errorMessage", "test error message"))
                .andExpect(status().isOk())
                .andExpect(view().name("userCreation"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("errorMessage"));

        assertNull(actions.andReturn().getRequest().getSession().getAttribute("errorMessage"));
    }

    @Test
    public void createUserInDB_shouldGetUserRequestWhichExistsInDBAndRedirectToAnotherController() throws Exception {

        BigInteger id = BigInteger.valueOf(1L);
        String email = "test@email.test";
        String firstName = "firstName";
        String lastName = "lastName";
        String password = "password";
        UserTypes type = UserTypes.REGULAR_USER;

        User user = new UserImpl.Builder(email, null)
                .buildId(id)
                .buildFirstName(firstName)
                .buildLastName(lastName)
                .buildType(type)
                .buildUser();

        when(userDaoMock.getUserByEmail(email)).thenReturn(user);

        ResultActions actions = mockMvc.perform(post("/user/create")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("email", email)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/create-user"))
                .andExpect(model().size(1));

        assertNotNull(actions.andReturn().getRequest().getSession().getAttribute("errorMessage"));
        verify(userDaoMock, times(1)).getUserByEmail(email);
        verifyNoMoreInteractions(userDaoMock);

    }

    @Test
    public void createUserInDB_shouldGetUserRequestAndStoreInDBAndRedirectToAnotherController() throws Exception {

        BigInteger id = BigInteger.valueOf(1L);
        String email = "test@email.test";
        String firstName = "firstName";
        String lastName = "lastName";
        String password = "password";
        UserTypes type = UserTypes.REGULAR_USER;

        User user = new UserImpl.Builder(email, null)
                .buildId(id)
                .buildFirstName(firstName)
                .buildLastName(lastName)
                .buildType(type)
                .buildUser();

        when(userDaoMock.getUserByEmail(email)).thenReturn(null);
        when(userDaoMock.createUser(
                firstName,
                lastName,
                email,
                password,
                type))
                .thenReturn(user);

        ResultActions actions = mockMvc.perform(post("/user/create")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("email", email)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/admin-panel"))
                .andExpect(model().size(1));

        assertNull(actions.andReturn().getRequest().getSession().getAttribute("errorMessage"));
        verify(userDaoMock, times(1)).getUserByEmail(email);
        verify(userDaoMock, times(1)).createUser(firstName, lastName, email, password, type);
        verifyNoMoreInteractions(userDaoMock);
    }

    @Test
    public void createUserInDB_shouldGetUserRequestWhichHaveIncorrectDataAndRedirectToAnotherController() throws Exception {

        String email = "incorrect@email";

        User user = new UserImpl.Builder(email, null)
                .buildUser();

        when(userDaoMock.getUserByEmail(email)).thenReturn(user);

        ResultActions actions = mockMvc.perform(post("/user/create")
                .param("firstName", "")
                .param("lastName", "")
                .param("email", email)
                .param("password", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/create-user"))
                .andExpect(model().size(1));

        assertNotNull(actions.andReturn().getRequest().getSession().getAttribute("errorMessage"));

    }

    @Test
    public void deleteUser_shouldGetUserIdAndDeleteUserWithThisIdFromDB() throws Exception {

        BigInteger id = BigInteger.valueOf(1L);
        String email = "test@email";
        String firstName = "firstName";
        String lastName = "lastName";
        UserTypes type = UserTypes.REGULAR_USER;

        User user = new UserImpl.Builder(email, null)
                .buildId(id)
                .buildFirstName(firstName)
                .buildLastName(lastName)
                .buildType(type)
                .buildUser();

        when(userDaoMock.getUserById(id)).thenReturn(user);
        when(userDaoMock.deleteUser(user)).thenReturn(true);

        mockMvc.perform(delete("/user/delete/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        verify(userDaoMock, times(1)).getUserById(id);
        verify(userDaoMock, times(1)).deleteUser(user);
        verifyNoMoreInteractions(userDaoMock);
    }

    @Ignore
    @Test
    public void getUserDashboard_shouldGetUserObjectFromSessionThenGetAllItsProjectsAndRenderToView() throws Exception {

        BigInteger id = BigInteger.valueOf(1L);
        String email = "test@email";
        String firstName = "firstName";
        String lastName = "lastName";
        UserTypes type = UserTypes.REGULAR_USER;

        User user = new UserImpl.Builder(email, null)
                .buildId(id)
                .buildFirstName(firstName)
                .buildLastName(lastName)
                .buildType(type)
                .buildUser();

        String name1 = "firstProject";
        String name2 = "secondProject";
        Date date1 = new Date();
        Date date2 = new Date();
        Project project1 = new DataVisualizationProject.Builder(name1, date1, id, user.getFullName()).buildProject();
        Project project2 = new DataVisualizationProject.Builder(name2, date2, id, user.getFullName()).buildProject();

        when(userDaoMock.getAllUserProjects(user, "creation_date", "desc", null)).thenReturn(Arrays.asList(project1, project2));

        ResultActions actions = mockMvc.perform(get("/user/dashboard").sessionAttr("userObject", user))
                .andExpect(status().isOk())
                .andExpect(view().name("userDashboard"))
                .andExpect(model().size(2))
                .andExpect(model().attribute("userProjects", hasSize(2)))
                .andExpect(model().attribute("userProjects", hasItem(
                        allOf(
                                hasProperty("name", is(name1)),
                                hasProperty("creationDate", is(date1)),
                                hasProperty("author", is(id))
                        )
                )))
                .andExpect(model().attribute("userProjects", hasItem(
                        allOf(
                                hasProperty("name", is(name2)),
                                hasProperty("creationDate", is(date2)),
                                hasProperty("author", is(id))
                        )
                )))
                .andExpect(model().attributeExists("userObject"));

        verify(userDaoMock, times(1)).getAllUserProjects(user, "creation_date", "desc", null);
        verifyNoMoreInteractions(userDaoMock);
    }
}
