package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.common.IdList;
import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.common.exceptions.ConnectionException;
import com.dreamteam.datavisualizator.common.exceptions.SelectorCreateException;
import com.dreamteam.datavisualizator.models.*;
import com.dreamteam.datavisualizator.models.impl.GraphicHMImpl;
import com.dreamteam.datavisualizator.models.impl.HealthMonitorProject;
import com.dreamteam.datavisualizator.models.impl.SelectorInstanceInfo;
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
import java.math.BigInteger;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class, HealthMonitorProjectDAOImplTest.HealthMonitorDaoCustomConfiguration.class})
public class HealthMonitorProjectDAOImplTest {

    @Autowired
    private HealthMonitorProjectDAO dao;

    @Autowired
    private UserDAO userDao;

    private User user;

    @Configuration
    public static class HealthMonitorDaoCustomConfiguration{
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


    private Project hmProject;

    @Before
    public void setUp(){

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

    public HealthMonitorProject setUpProject(){
        HealthMonitorProject project = (HealthMonitorProject) hmProject;
        project.setAuthor(user.getId());
        project.setAuthorFullName(user.getFullName());
        return (HealthMonitorProject) dao.saveProject(project);
    }

    @Test(expected = ConnectionException.class)
    public void setDataSourceTemplateWithIncorrectData(){
        HealthMonitorProject project = (HealthMonitorProject) hmProject;
        dao.setDataSourceTemplate(project.getServerName(), project.getPort(), project.getSid(), project.getUserName(), project.getPassword());
    }

    @Test
    @Transactional
    @Rollback
    public void getProjectGraphWithCcorrectProject(){
        user = userDao.createUser("testFirstName", "testLastName", "test6.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        HealthMonitorProject savedProject = setUpProject();
        Graphic expectedGraph = savedProject.getGraphic();
        Graphic actualGraph = dao.getProjectGraph(savedProject);
        assertEquals(expectedGraph.getId(), actualGraph.getId());
    }

    @Test
    public void getProjectGraphWithNullProject(){
        Graphic actualGraph = dao.getProjectGraph(null);
        assertNull(actualGraph);
    }

    @Test
    @Transactional
    @Rollback
    public void getProjectGraphWithIncorrectIdProject(){
        user = userDao.createUser("testFirstName", "testLastName", "test6.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        HealthMonitorProject savedProject = setUpProject();
        savedProject.setId(BigInteger.valueOf(100000000L));
        Graphic actualGraph = dao.getProjectGraph(savedProject);
        assertNull(actualGraph);
    }

    @Test(expected = SelectorCreateException.class)
    public void getProjectSelectorsWithNullProject(){
       dao.getProjectSelectors(null);
    }

    @Test
    @Transactional
    @Rollback
    public void getProjectSelectorWithCorrectProject(){
        user = userDao.createUser("testFirstName", "testLastName", "test6.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        HealthMonitorProject savedProject = setUpProject();
        Map<BigInteger, Selector> expectedMapSelectors = savedProject.getSelectors();
        Map<BigInteger, Selector> actualMapSelectors = dao.getProjectSelectors(savedProject);
        assertEquals(expectedMapSelectors.get(BigInteger.valueOf(8l)).getId(), actualMapSelectors.get(BigInteger.valueOf(8l)).getId());
    }

    @Test
    @Transactional
    @Rollback
    public void getProjectNameWithCorrectId(){
        user = userDao.createUser("testFirstName", "testLastName", "test6.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        HealthMonitorProject savedProject = setUpProject();
        String expected = savedProject.getName();
        String actual = dao.getProjectName(savedProject.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void getProjectNameWithIncorrectId(){
        String actual = dao.getProjectName(null);
        assertNull(actual);
    }

    @Test
    @Transactional
    @Rollback
    public void getProjectByIdWithCorrectId(){
        user = userDao.createUser("testFirstName", "testLastName", "test6.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        Project expectedProject = setUpProject();
        Project actualProject = dao.getProjectById(expectedProject.getId());
        assertEquals(expectedProject.getId(), actualProject.getId());
    }

    @Test
    public void getProjectByIdWithNullId(){
        Project actual = dao.getProjectById(null);
        assertNull(actual);
    }

    @Test//(expected = EmptyResultDataAccessException.class)
    public void getProjectByIdWithIncorrectId(){
        Project actual = dao.getProjectById(BigInteger.valueOf(100000L));
        assertNull(actual);
    }

    @Test
    @Transactional
    @Rollback
    public void getProjectsByAuthorWithCorrectData(){
        user = userDao.createUser("testFirstName", "testLastName", "test8.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        Project project1 = setUpProject();
        Project project2 = setUpProject();
        List<HealthMonitorProject> actualProjects = dao.getProjectsByAuthor(userDao.getUserById(project1.getAuthor()));
        assertTrue(actualProjects.size()>=2);
        assertNotNull(dao.getProjectById(project1.getId()));
        assertNotNull(dao.getProjectById(project2.getId()));
    }

    @Test
    public void getProjectsByAuthorWithInCorrectData(){
        List<HealthMonitorProject> actual = dao.getProjectsByAuthor(null);
        assertNull(actual);
    }

    @Test
    @Transactional
    @Rollback
    public void getProjectsUserHaveAccessToWithCorrectData(){
        user = userDao.createUser("testFirstName", "testLastName", "test8.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        Project project1 = setUpProject();
        Project project2 = setUpProject();
        User userSharedFor = userDao.createUser("testFirstName", "testLastName", "test10.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        userDao.giveUserAccessToProject(userSharedFor.getId(), project1.getId());
        userDao.giveUserAccessToProject(userSharedFor.getId(), project2.getId());
        List<HealthMonitorProject> actualProjects = dao.getProjectsUserHaveAccessTo(userSharedFor);
        assertTrue(actualProjects.size()>=2);
        assertNotNull(actualProjects.get(0));
        assertNotNull(actualProjects.get(1));
    }

    @Test
    public void getProjectsUserHaveAccessToWithInCorrectData(){
        List<HealthMonitorProject> actual = dao.getProjectsUserHaveAccessTo(null);
        assertNull(actual);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteHMProject_withCorrectParameter(){
        user = userDao.createUser("testFirstName", "testLastName", "test8.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        Project project = setUpProject();
        assertTrue(dao.deleteProject(project));
        assertNull(dao.getProjectById(project.getId()));
    }

    @Test
    public void deleteHMProject_withNullProject(){
        assertFalse(dao.deleteProject(null));
    }

    @Test
    @Transactional
    @Rollback
    public void saveProjectWithCorrectParameter(){
        user = userDao.createUser("testFirstName", "testLastName", "test8.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        Project expectedProject = setUpProject();
        Project actualProject = dao.getProjectById(expectedProject.getId());
        assertEquals(expectedProject.getId(), actualProject.getId());
    }

    @Test
    public void saveProjectWithInCorrectParameter(){
        assertNull(dao.saveProject(null));
    }

    @Test
    @Transactional
    @Rollback
    public void saveProjectWithNullSelectors(){
        user = userDao.createUser("testFirstName", "testLastName", "test8.email@email.com", "testpassword", UserTypes.REGULAR_USER);
        HealthMonitorProject project = (HealthMonitorProject) hmProject;
        project.setAuthor(user.getId());
        project.setAuthorFullName(user.getFullName());
        project.setSelectors(null);
        assertNull(dao.saveProject(project));
    }

    @Test
    public void createSelectorsWithCorrectSettings(){
        Map<BigInteger, String> mapSelectors = new HashMap<BigInteger, String>();
        mapSelectors.put(IdList.S_INSTANCE_INFO_OBJTYPE_ID,  null);
        mapSelectors.put(IdList.S_SIZE_TABLESPACE_OBJTYPE_ID,null);
        mapSelectors.put(IdList.S_SIZE_INDEX_LOB_OBJTYPE_ID, "user");
        mapSelectors.put(IdList.S_LAST_ERRORS_OBJTYPE_ID,    null);
        mapSelectors.put(IdList.S_ACTIVE_SESSIONS_OBJTYPE_ID,"10");
        mapSelectors.put(IdList.S_ACTIVE_QUERIES_OBJTYPE_ID, "10");
        dao.setDataSourceTemplate("94.158.152.89","8080", "ORCL", System.getenv("TEST_SQL_LOGIN"), System.getenv("TEST_SQL_PASSWORD"));
        Map<BigInteger, Selector> actualMapSelectors =  dao.createSelectorList(mapSelectors);
        assertTrue(actualMapSelectors.size() == 6);
        assertNotNull(actualMapSelectors.get(IdList.S_INSTANCE_INFO_OBJTYPE_ID));
        assertNotNull(actualMapSelectors.get(IdList.S_SIZE_TABLESPACE_OBJTYPE_ID));
        assertNotNull(actualMapSelectors.get(IdList.S_SIZE_INDEX_LOB_OBJTYPE_ID));
        assertNotNull(actualMapSelectors.get(IdList.S_LAST_ERRORS_OBJTYPE_ID));
        assertNotNull(actualMapSelectors.get(IdList.S_ACTIVE_SESSIONS_OBJTYPE_ID));
        assertNotNull(actualMapSelectors.get(IdList.S_ACTIVE_QUERIES_OBJTYPE_ID));
    }

    @Test(expected = SelectorCreateException.class)
    public void createSelectorsWithNullSettings(){
          Map<BigInteger, Selector> actualMapSelectors =  dao.createSelectorList(null);
    }

    @Test
    public void createGraphWithCorrectSettings(){
        dao.setDataSourceTemplate("94.158.152.89","8080", "ORCL", System.getenv("TEST_SQL_LOGIN"), System.getenv("TEST_SQL_PASSWORD"));
        Graphic actualGraph =  dao.createGraph(1000);
        assertNotNull(actualGraph);
    }

}
