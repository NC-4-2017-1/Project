package com.dreamteam.datavisualizator.common.configurations;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class HMDataSource {
    private String url;
    private String username;
    private String password;

    public HMDataSource(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public DriverManagerDataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

   /* public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@issue.pp.ua:8080/ORCL";
        HMDataSource dataSourceHM = new HMDataSource(url,"system", "130703ce");
        JdbcTemplate templateHM = new JdbcTemplate(dataSourceHM.createDataSource());
        Object o = templateHM.queryForObject("select sys_context('userenv', 'DB_NAME')   from dual",  String.class);
        System.out.println((String)o);
    }*/
}
