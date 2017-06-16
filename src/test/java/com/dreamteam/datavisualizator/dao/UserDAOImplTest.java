package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.models.*;
import com.dreamteam.datavisualizator.models.impl.*;
import com.google.gson.JsonObject;
import org.junit.Before;
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
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class, UserDAOImplTest.UserDaoCustomConfiguration.class})
public class UserDAOImplTest {

    @Autowired
    private UserDAO dao;

    @Autowired
    private DataVisualizationProjectDAO dvDao;

    @Autowired
    private HealthMonitorProjectDAO hmDao;

    @Configuration
    public static class UserDaoCustomConfiguration{
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

    private Project hmProject;

    @Before
    public void setUp(){
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

        Graphic hmGraphic = new GraphicHMImpl.HMGraphBuilder()
                .buildGraphicJson(new JsonObject())
                .buildHourCount(12)
                .buildName("testHMGraph")
                .buildGraphic();

        Selector testSelector = new SelectorInstanceInfo();
        testSelector.setName("Instance information");
        Map<BigInteger, Selector> selectors = new HashMap<>();
        selectors.put(BigInteger.valueOf(8L), testSelector);

        hmProject = new HealthMonitorProject.Builder(null,"HMTestProject", new Date(), "HMTestProjectDescription", null, null, "sid", "port", "server", "user", "password")
                .buildDescription("HMTestProjectDescription")
                .buildGraphic(hmGraphic)
                .buildSelectors(selectors)
                .buildProject();
    }

    @Test
    @Transactional
    @Rollback
    public void getUserByIdWithCorrectId(){
        User expectedUser = dao.createUser("testFirstName", "testLastName", "test.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        User actualUser = dao.getUserById(expectedUser.getId());
        assertEquals(expectedUser.getId(), actualUser.getId());
    }

    @Test
    public void getUserByIdWithIncorrectId(){
        User actualUser = dao.getUserById(null);
        assertNull(actualUser);
    }

    @Test
    @Transactional
    @Rollback
    public void getUserByEmailWithCorrectEmail(){
        User expectedUser = dao.createUser("testFirstName", "testLastName", "test.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        User actualUser = dao.getUserByEmail(expectedUser.getEmail());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @Test
    public void getUserByEmailWithIncorrectEmail(){
        User actualUser = dao.getUserByEmail(null);
        assertNull(actualUser);
    }

    @Test
    @Transactional
    @Rollback
    public void getAllUsersList(){
        User user1 = dao.createUser("testFirstName1", "testLastName1", "test.email@email.com1", "testpassword1", UserTypes.REGULAR_USER);
        User user2 = dao.createUser("testFirstName2", "testLastName2", "test.email@email.com2", "testpassword2", UserTypes.REGULAR_USER);
        List users = dao.getAllUsersList(0, null);
        assertTrue(users.size()>=2);
        assertNotNull(dao.getUserById(user1.getId()));
        assertNotNull(dao.getUserById(user2.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteUser_withCorrectParameter(){
        User user = dao.createUser("testFirstName", "testLastName", "test.email@email.com", "password", UserTypes.REGULAR_USER);

        dvProject.setAuthor(user.getId());
        dvProject.setAuthorFullName(user.getFullName());
        hmProject.setAuthor(user.getId());
        hmProject.setAuthorFullName(user.getFullName());

        dvDao.saveProject(dvProject);
        hmDao.saveProject(hmProject);

        assertTrue(dao.deleteUser(user));
        assertNull(dao.getUserById(user.getId()));
        assertTrue(dao.getAllUserProjects(user, 4, "desc").equals(Collections.<Project>emptyList()));
    }

    @Test
    @Transactional
    @Rollback
    public void giveUserAccessToProjectWithCorrectParameters(){
        User user1 = dao.createUser("testFirstName1", "testLastName1", "test.email@email.com1", "testpassword1", UserTypes.REGULAR_USER);
        User user2 = dao.createUser("testFirstName2", "testLastName2", "test.email@email.com2", "testpassword2", UserTypes.REGULAR_USER);
        dvProject.setAuthor(user1.getId());
        dvProject.setAuthorFullName(user1.getFullName());
        dvProject = dvDao.saveProject(dvProject);
        assertTrue(dao.giveUserAccessToProject(user2, dvProject));
        assertTrue(dao.getAllSharedToUserProjects(user2, 4, "desc").size()==1);
    }

    @Test
    @Transactional
    @Rollback
    public void giveUserAccessToProjectWithNullParameters(){
        assertFalse(dao.giveUserAccessToProject(null, null));
    }

    @Test
    @Transactional
    @Rollback
    public void removeAccessToProjectFromUserWithCorrectParameters(){
        User user1 = dao.createUser("testFirstName1", "testLastName1", "test.email@email.com1", "testpassword1", UserTypes.REGULAR_USER);
        User user2 = dao.createUser("testFirstName2", "testLastName2", "test.email@email.com2", "testpassword2", UserTypes.REGULAR_USER);
        dvProject.setAuthor(user1.getId());
        dvProject.setAuthorFullName(user1.getFullName());
        dvProject = dvDao.saveProject(dvProject);
        dao.giveUserAccessToProject(user2, dvProject);
        assertTrue(dao.getAllSharedToUserProjects(user2, 4, "desc").size()==1);
        dao.removeAccessToProjectFromUser(user2, dvProject);
        assertTrue(dao.getAllSharedToUserProjects(user2, 4, "desc").isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void removeAccessToProjectFromUserWithNullParameters(){
        assertFalse(dao.removeAccessToProjectFromUser(null, null));
    }

    @Test
    @Transactional
    @Rollback
    public void createUserWithCorrectParameter(){
        User userExpected = dao.createUser("testFirstName", "testLastName", "test.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        User userActual = dao.getUserById(userExpected.getId());
        assertEquals(userExpected.getFirstName(), userActual.getFirstName());
    }

    @Test
    @Transactional
    @Rollback
    public void createUserWithEmailWhichAlreadyExists(){
        User userExpected = dao.createUser("testFirstName", "testLastName", "test.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        User userActual = dao.createUser(userExpected.getFirstName(), userExpected.getLastName(), userExpected.getEmail(), userExpected.getPassword(), userExpected.getUserType());
        assertNull(userActual);
    }

    @Test
    @Transactional
    @Rollback
    public void getUsersThatHaveAccessToProjectWitCorrectParameter(){
        User user1 = dao.createUser("testFirstName1", "testLastName1", "test.email@email.com1", "testpassword1", UserTypes.REGULAR_USER);
        User user2 = dao.createUser("testFirstName2", "testLastName2", "test.email@email.com2", "testpassword2", UserTypes.REGULAR_USER);
        dvProject.setAuthor(user1.getId());
        dvProject.setAuthorFullName(user1.getFullName());
        dvProject = dvDao.saveProject(dvProject);
        dao.giveUserAccessToProject(user2, dvProject);

        List<User> usersThatHaveAccessToProject = dao.getUsersThatHaveAccessToProject(dvProject);

        assertTrue(usersThatHaveAccessToProject.size()==1);
        assertEquals(user2.getId(), usersThatHaveAccessToProject.get(0).getId());
    }

    @Test
    public void getUsersThatHaveAccessToProjectWitNullParameter(){
        assertNull(dao.getUsersThatHaveAccessToProject(null));
    }

    @Test
    @Transactional
    @Rollback
    public void getAllUserProjectsWithCorrectParameter(){
        User user = dao.createUser("testFirstName", "testLastName", "test.email@email.com", "password", UserTypes.REGULAR_USER);
        dvProject.setAuthor(user.getId());
        dvProject.setAuthorFullName(user.getFullName());
        hmProject.setAuthor(user.getId());
        hmProject.setAuthorFullName(user.getFullName());
        dvDao.saveProject(dvProject);
        hmDao.saveProject(hmProject);
        List<Project> allUserProjects = dao.getAllUserProjects(user, 4, "desc");

        assertTrue(allUserProjects.size()==2);
    }

    @Test
    public void getAllUserProjectsWithNullParameters(){
        assertNull(dao.getAllUserProjects(null, 4, null));
    }

    @Test
    @Transactional
    @Rollback
    public void getAllSharedToUserProjectsWithCorrectParameter(){
        User user1 = dao.createUser("testFirstName1", "testLastName1", "test.email@email.com1", "testpassword1", UserTypes.REGULAR_USER);
        User user2 = dao.createUser("testFirstName2", "testLastName2", "test.email@email.com2", "testpassword2", UserTypes.REGULAR_USER);
        dvProject.setAuthor(user1.getId());
        dvProject.setAuthorFullName(user1.getFullName());
        dvProject = dvDao.saveProject(dvProject);
        dao.giveUserAccessToProject(user2, dvProject);

        List<Project> allSharedToUserProjects = dao.getAllSharedToUserProjects(user2, 4, "desc");

        assertTrue(allSharedToUserProjects.size()==1);
        assertEquals(ProjectTypes.DATA_VISUALIZATION, allSharedToUserProjects.get(0).getType());
    }

    @Test
    public void getAllSharedToUserProjectsWithNullParameter(){
        assertNull(dao.getAllSharedToUserProjects(null, 4, null));
    }


}