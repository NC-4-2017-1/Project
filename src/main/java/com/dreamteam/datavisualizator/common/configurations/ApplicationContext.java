
package com.dreamteam.datavisualizator.common.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ApplicationContext {

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl(System.getenv("SQL_JDBC_URL"));
        dataSource.setUsername(System.getenv("SQL_LOGIN"));
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="generalTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name="simpleCallTemplate")
    public SimpleJdbcCall getSimpleJdbcCall(JdbcTemplate generalTemplate){
        return new SimpleJdbcCall(generalTemplate);
    }
}
