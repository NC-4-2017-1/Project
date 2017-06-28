package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.GraphicDVImpl;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class, DataVisualizationProjectDAOImplTest.DataVisualizationDaoCustomConfiguration.class})
public class DataVisualizationProjectDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    private User user;

    @Autowired
    private DataVisualizationProjectDAO dvDao;

    @Configuration
    public static class DataVisualizationDaoCustomConfiguration {
        @Bean(name = "dataSource")
        public DataSource getDataSource() {
            TimeZone timeZone = TimeZone.getTimeZone("Europe/Helsinki");
            TimeZone.setDefault(timeZone);
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            dataSource.setUrl(System.getenv("TEST_SQL_JDBC_URL"));
            dataSource.setUsername(System.getenv("TEST_SQL_LOGIN"));
            dataSource.setPassword(System.getenv("TEST_SQL_PASSWORD"));
            return dataSource;
        }
    }

    private Project dvProject;

    @Before
    public void setUp() {
        List<Graphic> graphics = new ArrayList<>();
        Graphic dvGraphic = new GraphicDVImpl.DVGraphBuilder()
                .buildGraphicJSON(new JsonObject())
                .buildAverage(BigDecimal.valueOf(12.5))
                .buildDispersion(BigDecimal.valueOf(12.5))
                .buildOlympicAverage(BigDecimal.valueOf(12.5))
                .buildCorrelation(null)
                .buildMathExpectation(BigDecimal.valueOf(12.5))
                .buildName("testDVGraph")
                .buildGraphic();

        graphics.add(dvGraphic);

        dvProject = new DataVisualizationProject.Builder("DVTestProject", new Date(), null, null)
                .buildDescription("DVTestProjectDescription")
                .buildGraphics(graphics)
                .buildProject();

    }

    public DataVisualizationProject setUpProjectDV(){
        DataVisualizationProject project = (DataVisualizationProject) dvProject;
        project.setAuthor(user.getId());
        project.setAuthorFullName(user.getFullName());
        return (DataVisualizationProject) dvDao.saveProject(project);
    }

    @Test
    @Transactional
    @Rollback
    public void getProjectByIdWithCorrectId(){
        user = userDAO.createUser("testFirstName", "testLastName", "test.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        Project expectedProject = setUpProjectDV();
        Project actualProject = dvDao.getProjectById(expectedProject.getId());
        assertEquals(expectedProject.getId(), actualProject.getId());
    }

    @Test
    public void getProjectByIdWithNullId(){
        Project actual = dvDao.getProjectById(null);
        assertNull(actual);
    }

    @Test
    public void getProjectByIdWithIncorrectId(){
        Project actual = dvDao.getProjectById(BigInteger.valueOf(500L));
        assertNull(actual);
    }

    @Test
    @Transactional
    @Rollback
    public void getProjectNameWithCorrectId(){
        user = userDAO.createUser("testFirstName", "testLastName", "test.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        DataVisualizationProject savedProject = setUpProjectDV();
        String expected = savedProject.getName();
        String actual = dvDao.getProjectName(savedProject.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void getProjectNameWithIncorrectId(){
        String actual = dvDao.getProjectName(null);
        assertNull(actual);
    }



    @Test
    @Transactional
    @Rollback
    public void getProjectsByAuthorWithCorrectData(){
        user = userDAO.createUser("testFirstName", "testLastName", "test.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        Project project1 = setUpProjectDV();
        Project project2 = setUpProjectDV();
        List<DataVisualizationProject> actualProjects = dvDao.getProjectsByAuthor(userDAO.getUserById(project1.getAuthor()));
        assertTrue(actualProjects.size()>=2);
        assertNotNull(dvDao.getProjectById(project1.getId()));
        assertNotNull(dvDao.getProjectById(project2.getId()));
    }

    @Test
    public void getProjectsByAuthorWithInCorrectData(){
        List<DataVisualizationProject> actual = dvDao.getProjectsByAuthor(null);
        assertNull(actual);
    }

}
