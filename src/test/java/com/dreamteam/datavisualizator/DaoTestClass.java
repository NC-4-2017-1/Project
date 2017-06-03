package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.dao.impl.DataVisualizationProjectDAOImpl;
import com.dreamteam.datavisualizator.dao.impl.HealthMonitorProjectDAOImpl;
import com.dreamteam.datavisualizator.dao.impl.UserDAOImpl;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class DaoTestClass {
    private static final Logger LOGGER = Logger.getLogger(DaoTestClass.class);

    //dao
    private DataVisualizationProjectDAOImpl dataVisualizationProjectDAO;
    private HealthMonitorProjectDAOImpl healthMonitorProjectDAO;
    private UserDAOImpl userDAO;

    //context
    private DataSource dataSource;
    private DataSourceTransactionManager transactionManager;
    private JdbcTemplate generalTemplate;
    private SimpleJdbcCall simpleCallTemplate;

    void raiseContext() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(com.dreamteam.datavisualizator.common.configurations.ApplicationContext.class);
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        transactionManager = (DataSourceTransactionManager) applicationContext.getBean("transactionManager");
        generalTemplate = (JdbcTemplate) applicationContext.getBean("generalTemplate");
        simpleCallTemplate = (SimpleJdbcCall) applicationContext.getBean("simpleCallTemplate");
    }


    void raiseDAO() {

        dataVisualizationProjectDAO = new DataVisualizationProjectDAOImpl();
        healthMonitorProjectDAO = new HealthMonitorProjectDAOImpl();
        userDAO = new UserDAOImpl();

        Class<DataVisualizationProjectDAOImpl> classDV = DataVisualizationProjectDAOImpl.class;
        Class<HealthMonitorProjectDAOImpl> classHM = HealthMonitorProjectDAOImpl.class;
        Class<UserDAOImpl> classU = UserDAOImpl.class;


        Field simpleCallDV = null;
        Field jdbcTemplateDV = null;
        Field userDaoDV = null;

        Field simpleCallHM = null;
        Field jdbcTemplateHM = null;
        Field userDaoHM = null;

        Field simpleCallU = null;
        Field jdbcTemplateU = null;
        Field hmU = null;
        Field dvU = null;

        try {
            simpleCallDV = classDV.getDeclaredField("simpleCallTemplate");
            jdbcTemplateDV = classDV.getDeclaredField("generalTemplate");
            simpleCallHM = classHM.getDeclaredField("simpleCallTemplate");
            jdbcTemplateHM = classHM.getDeclaredField("generalTemplate");
//            simpleCallU = classU.getDeclaredField("simpleCallTemplate");
            jdbcTemplateU = classU.getDeclaredField("generalTemplate");
            userDaoDV = classDV.getDeclaredField("userDAO");
            userDaoHM = classHM.getDeclaredField("userDAO");
            hmU = classU.getDeclaredField("healthMonitorProjectDAO");
            dvU = classU.getDeclaredField("dataVisualizationProjectDAO");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        simpleCallDV.setAccessible(true);
        jdbcTemplateDV.setAccessible(true);
        simpleCallHM.setAccessible(true);
        jdbcTemplateHM.setAccessible(true);
//        simpleCallU.setAccessible(true);
        jdbcTemplateU.setAccessible(true);
        userDaoDV.setAccessible(true);
        userDaoHM.setAccessible(true);
        hmU.setAccessible(true);
        dvU.setAccessible(true);


        try {
            simpleCallDV.set(dataVisualizationProjectDAO, simpleCallTemplate);
            jdbcTemplateDV.set(dataVisualizationProjectDAO, generalTemplate);
            simpleCallHM.set(healthMonitorProjectDAO, simpleCallTemplate);
            jdbcTemplateHM.set(healthMonitorProjectDAO, generalTemplate);
            //        simpleCallU.set(userDAO, simpleCallTemplate);
            jdbcTemplateU.set(userDAO, generalTemplate);
            userDaoDV.set(dataVisualizationProjectDAO, userDAO);
            userDaoHM.set(healthMonitorProjectDAO, userDAO);
            dvU.set(userDAO, dataVisualizationProjectDAO);
            hmU.set(userDAO, healthMonitorProjectDAO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        DaoTestClass bt = new DaoTestClass();
        bt.raiseContext();
        bt.raiseDAO();
        bt.codeGoesHere();
    }

    void codeGoesHere() {

    }


    void test8() { // passed
        // public List<User> getUsersThatHaveAccessToProject(Project project)
        List<User> users = userDAO.getUsersThatHaveAccessToProject(dataVisualizationProjectDAO.getProjectById(BigInteger.valueOf(57l)));
        for (User user : users) {
            LOGGER.info(user);
        }
    }

    void test7() { // passed
        //public List<Graphic> getProjectGraphs(Project project)
        List<Graphic> graphs = dataVisualizationProjectDAO.getProjectGraphs(dataVisualizationProjectDAO.getProjectById(BigInteger.valueOf(57l)));
        for (Graphic graph : graphs) {
            LOGGER.info(graph);
        }
    }

    void test6() {//passed
        /*
        public Project saveProject(String name, BigInteger authorId, String description, List<Graphic> graphics) {
        private GraphicDVImpl saveGraphic(GraphicDVImpl graphic, BigInteger projectId) {
        private GraphicDVImpl getGraphById(BigInteger graphicId) {
        * */

        DataVisualizationProject prFromDb = (DataVisualizationProject) dataVisualizationProjectDAO.getProjectById(BigInteger.valueOf(57l));
        DataVisualizationProject prGenerated = new DataVisualizationProject.Builder("lasd213", new Date(), prFromDb.getAuthor()).buildGraphics(prFromDb.getGraphics()).buildProject();

        Project project = dataVisualizationProjectDAO.saveProject(prGenerated);

        LOGGER.info(project.getId() + " " + project.getName() + " " +
                project.getAuthor() + " " + project.getCreationDate() + " " + project.getDescription() + " " +
                project.getType() + " " + project.getUsersProjectAccessibleTo());
    }

    void test5() { //passed
        //public boolean deleteProject(Project project) {
        dataVisualizationProjectDAO.deleteProject(dataVisualizationProjectDAO.getProjectById(BigInteger.valueOf(71l)));
    }

    void test4() {  //passed
        //public List<DataVisualizationProject> getProjectsUserHaveAccessTo(User user) {
        User user = new UserImpl.Builder("123", "").buildId(BigInteger.valueOf(46l)).buildUser();
        List<DataVisualizationProject> projects = dataVisualizationProjectDAO.getProjectsUserHaveAccessTo(user);
        LOGGER.info(projects + " opr");

        for (DataVisualizationProject projectById : projects) {
            LOGGER.info(projectById.getId() + " " + projectById.getName() + " " +
                    projectById.getAuthor() + " " + projectById.getCreationDate() + " " + projectById.getDescription() + " " +
                    projectById.getType() + " " + projectById.getUsersProjectAccessibleTo());
        }
    }

    void test3() { // passed
        //public List<DataVisualizationProject> getProjectsByAuthor(User user)
        User user = new UserImpl.Builder("123", "").buildId(BigInteger.valueOf(46l)).buildUser();
        List<DataVisualizationProject> projects = dataVisualizationProjectDAO.getProjectsByAuthor(user);
        LOGGER.info(projects + " opr");

        for (DataVisualizationProject projectById : projects) {
            LOGGER.info(projectById.getId() + " " + projectById.getName() + " " +
                    projectById.getAuthor() + " " + projectById.getCreationDate() + " " + projectById.getDescription() + " " +
                    projectById.getType() + " " + projectById.getUsersProjectAccessibleTo());
        }
    }


    void test2() { // passed
        //public Project getProjectByName(String projectName) {
        Project projectById = dataVisualizationProjectDAO.getProjectByName("dv_project_6");
        LOGGER.info(projectById.getId() + " " + projectById.getName() + " " +
                projectById.getAuthor() + " " + projectById.getCreationDate() + " " + projectById.getDescription() + " " +
                projectById.getType() + " " + projectById.getUsersProjectAccessibleTo());


    }

    void test1() { // passed
        //public Project getProjectById(BigInteger id)
        Project projectById = dataVisualizationProjectDAO.getProjectById(BigInteger.valueOf(57l));
        LOGGER.info(projectById.getId() + " " + projectById.getName() + " " +
                projectById.getAuthor() + " " + projectById.getCreationDate() + " " + projectById.getDescription() + " " +
                projectById.getType() + " " + projectById.getUsersProjectAccessibleTo());

    }

}
