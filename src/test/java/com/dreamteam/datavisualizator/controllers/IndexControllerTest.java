package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigInteger;

import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class})
public class IndexControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void tryToLogIn_shouldGetUserFromSessionAndIfRoleAdminRedirectToAdminPanel() throws Exception {

        User user = new UserImpl.Builder("test@email1", null)
                .buildId(BigInteger.valueOf(1L))
                .buildFirstName("FirstName1")
                .buildLastName("LastName1")
                .buildType(UserTypes.ADMIN)
                .buildUser();

        mockMvc.perform(get("/login").sessionAttr("userObject", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/admin-panel"));

        mockMvc.perform(get("/").sessionAttr("userObject", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/admin-panel"));
    }

    @Test
    public void tryToLogIn_shouldGetUserFromSessionAndIfRoleRegularUserRedirectToDashboard() throws Exception {

        User user = new UserImpl.Builder("test@email1", null)
                .buildId(BigInteger.valueOf(1L))
                .buildFirstName("FirstName1")
                .buildLastName("LastName1")
                .buildType(UserTypes.REGULAR_USER)
                .buildUser();

        mockMvc.perform(get("/login").sessionAttr("userObject", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard-get/4/desc"));

        mockMvc.perform(get("/").sessionAttr("userObject", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard-get/4/desc"));
    }

    @Test
    public void tryToLogIn_shouldGetUserFromSessionAndIfNullReturnAuthorizationView() throws Exception {

        MvcResult result1 = mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("authorization")).andReturn();

        MvcResult result2 = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("authorization")).andReturn();

        assertNull(result1.getRequest().getSession().getAttribute("userObject"));
        assertNull(result2.getRequest().getSession().getAttribute("userObject"));
    }

}