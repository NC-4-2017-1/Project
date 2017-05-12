
package com.dreamteam.datavisualizator.common.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ApplicationContext {

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(System.getenv(""));
        dataSource.setUrl(System.getenv(""));
        dataSource.setUsername(System.getenv(""));
        dataSource.setPassword(System.getenv(""));
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
