package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.dao.impl.HealthMonitorProjectDAOImpl;
import com.dreamteam.datavisualizator.dao.impl.UserDAOImpl;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.HealthMonitorProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.math.BigInteger;

public class BeforeTemplate {


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

    public static void main(String[] args) {
        BeforeTemplate bt = new BeforeTemplate();
        bt.raiseContext();
        bt.codeGoesHere();
    }

    void codeGoesHere() {
        HealthMonitorProjectDAO healthMonitorProjectDAO = new HealthMonitorProjectDAOImpl();
        UserDAO userDAO= new UserDAOImpl();
        User user=userDAO.getUserById(BigInteger.valueOf(42));
        System.out.println(user.getEmail());
        System.out.println(user.getFirstName());
       /* Project healthMonitorProject = healthMonitorProjectDAO.getProjectById(BigInteger.valueOf(87L));
        System.out.println(healthMonitorProject.getCreationDate());
        System.out.println(healthMonitorProject.getName());
        System.out.println(healthMonitorProject.getId());*/
        //System.out.println(healthMonitorProject.getCreationDate());
    }


}
