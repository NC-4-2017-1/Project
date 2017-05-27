package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.dao.impl.DataVisualizationProjectDAOImpl;
import com.dreamteam.datavisualizator.dao.impl.HealthMonitorProjectDAOImpl;
import com.dreamteam.datavisualizator.dao.impl.UserDAOImpl;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
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

        Field simpleCallHM = null;
        Field jdbcTemplateHM = null;

        Field simpleCallU = null;
        Field jdbcTemplateU = null;


        try {
            simpleCallDV = classDV.getDeclaredField("simpleCallTemplate");
            jdbcTemplateDV = classDV.getDeclaredField("generalTemplate");
            simpleCallHM = classHM.getDeclaredField("simpleCallTemplate");
            jdbcTemplateHM = classHM.getDeclaredField("generalTemplate");
//            simpleCallU = classU.getDeclaredField("simpleCallTemplate");
            jdbcTemplateU = classU.getDeclaredField("generalTemplate");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        simpleCallDV.setAccessible(true);
        jdbcTemplateDV.setAccessible(true);
        simpleCallHM.setAccessible(true);
        jdbcTemplateHM.setAccessible(true);
//        simpleCallU.setAccessible(true);
        jdbcTemplateU.setAccessible(true);

        try {
            simpleCallDV.set(dataVisualizationProjectDAO, simpleCallTemplate);
            jdbcTemplateDV.set(dataVisualizationProjectDAO, generalTemplate);
            simpleCallHM.set(healthMonitorProjectDAO, simpleCallTemplate);
            jdbcTemplateHM.set(healthMonitorProjectDAO, generalTemplate);
            //        simpleCallU.set(userDAO, simpleCallTemplate);
            jdbcTemplateU.set(userDAO, generalTemplate);
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

        Project proj = dataVisualizationProjectDAO.getProjectByName("dv_project_1");
        LOGGER.info(proj);
        LOGGER.info("started selecting graphs");
        List<Graphic> projectGraphs = dataVisualizationProjectDAO.getProjectGraphs(new DataVisualizationProject.Builder
                ("lul", new Date(), BigInteger.valueOf(52L)).buildId(BigInteger.valueOf(52L)).buildProject());
        LOGGER.info(projectGraphs);
        for (Graphic graph : projectGraphs) {
            LOGGER.info(graph);
        }
    }

}
