package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.common.configurations.ServletContext;
import com.dreamteam.datavisualizator.models.Project;
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
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServletContext.class, DataVisualizationProjectDAOImplTest.DataVisualizationDaoCustomConfiguration.class})
public class DataVisualizationProjectDAOImplTest {

    @Autowired
    private UserDAO dao;

    @Autowired
    private DataVisualizationProjectDAO dvDao;

    @Autowired
    private HealthMonitorProjectDAO hmDao;

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

    @Ignore
    @Test
    @Transactional
    @Rollback
    public void getUserByIdWithCorrectId() {
        assertEquals(new Object(), new Object());
    }

}
