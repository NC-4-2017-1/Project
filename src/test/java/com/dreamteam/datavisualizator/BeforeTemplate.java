package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.dao.impl.DataVisualizationProjectDAOImpl;
import com.dreamteam.datavisualizator.dao.impl.HealthMonitorProjectDAOImpl;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.impl.GraphicHMImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.math.BigInteger;

public class BeforeTemplate {
    private static final Logger LOGGER = Logger.getLogger(BeforeTemplate.class);

    //dao
    private DataVisualizationProjectDAOImpl dataVisualizationProjectDAO;
    private HealthMonitorProjectDAOImpl healthMonitorProjectDAO;

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

        Class<DataVisualizationProjectDAOImpl> classDV = DataVisualizationProjectDAOImpl.class;
        Class<HealthMonitorProjectDAOImpl> classHM = HealthMonitorProjectDAOImpl.class;

        Field simpleCallDV = null;
        Field jdbcTemplateDV = null;

        Field simpleCallHM = null;
        Field jdbcTemplateHM = null;


        //!TODO user fields
        try {
            simpleCallDV = classDV.getDeclaredField("simpleCallTemplate");
            jdbcTemplateDV = classDV.getDeclaredField("generalTemplate");
            simpleCallHM = classHM.getDeclaredField("simpleCallTemplate");
            jdbcTemplateHM = classHM.getDeclaredField("generalTemplate");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        simpleCallDV.setAccessible(true);
        jdbcTemplateDV.setAccessible(true);
        simpleCallHM.setAccessible(true);
        jdbcTemplateHM.setAccessible(true);

        try {
            simpleCallDV.set(dataVisualizationProjectDAO, simpleCallTemplate);
            jdbcTemplateDV.set(dataVisualizationProjectDAO, generalTemplate);
            simpleCallHM.set(healthMonitorProjectDAO, simpleCallTemplate);
            jdbcTemplateHM.set(healthMonitorProjectDAO, generalTemplate);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        BeforeTemplate bt = new BeforeTemplate();
        bt.raiseContext();
        bt.raiseDAO();
        bt.codeGoesHere();
    }

    void codeGoesHere() {
        Project proj = healthMonitorProjectDAO.getProjectById(BigInteger.valueOf(77L));
        Graphic graphic = healthMonitorProjectDAO.getProjectGraphic(proj);
        LOGGER.info(proj.getName() + " " + proj.getType());
        LOGGER.info(graphic);
        LOGGER.info(graphic.getId());
        LOGGER.info(graphic.getName());
        LOGGER.info(((GraphicHMImpl)graphic).getHourCount());
        //LOGGER.info(graphic.getGraphicJSON());

    }

}
