package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.common.configurations.HMDataSource;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class HealthMonitorProjectDAOImpl implements HealthMonitorProjectDAO {
    private JdbcTemplate templateHM;

    private enum HWProjectColumnName {ID, NAME, CREATION_DATE, AUTHOR, DESCRIPTION, SID, PORT, SERVER_NAME, USER_NAME, PASSWORD}

    @Autowired
    private JdbcTemplate generalTemplate;

    @Autowired
    private SimpleJdbcCall simpleCallTemplate;

    public void setDataSourceTemplate(String serverName, String port, String sid, String username, String password) {
        String url = "jdbc:oracle:thin:@" + serverName + ":" + port + "/" + sid;
        HMDataSource dataSourceHM = new HMDataSource(url, username, password);
        templateHM = new JdbcTemplate(dataSourceHM.createDataSource());
    }

    public Graphic getProjectGraphic(Project project) {
        return null;
    }

    public List<Selector> getProjectSelectors(Project project) {
        return null;
    }

    public Map<String, String> getProjectConnections(Project project) {  return null;  }

    public Graphic createGraphic(int hourCount) {
        return null;
    }

    public List<Selector> createSelectorList(Map<String, String> map) {
        return null;
    }

    //public boolean saveGraphic(int hourCount, Graphic graphic) { return false;  }
    //public boolean saveSelector(Map<String, String> map, Selector selector) {return false;}

    public Project getProjectById(BigInteger id) {
        return null;
    }

    public Project getProjectByName(String projectName) {
        return null;
    }

    public List<Project> getProjectsByAuthor(User user) {
        return null;
    }

    public List<Project> getProjectsUserHaveAccessTo(User user) {
        return null;
    }

    public boolean deleteProject(Project project) {
        return false;
    }

    public boolean saveProject(Project project) {
        generalTemplate.update(INSERT_HM_PROJECT, project.getName(), project.getDescription(), project.getAuthor().toString());
        return true;
    }

    public void getPreviewProjectDataForUser(User user) {

    }

    private class HealthMonitorProjectRowMapper implements RowMapper {
        public HealthMonitorProject mapRow(ResultSet rs, int rownum) throws SQLException {
            HealthMonitorProject.Builder builder = new HealthMonitorProject.Builder(
                    BigInteger.valueOf(rs.getLong(HWProjectColumnName.ID.toString())),
                    rs.getString(HWProjectColumnName.NAME.toString()),
                    rs.getDate(HWProjectColumnName.CREATION_DATE.toString()),
                    BigInteger.valueOf(rs.getLong(HWProjectColumnName.AUTHOR.toString())),
                    rs.getString(HWProjectColumnName.SID.toString()),
                    rs.getString(HWProjectColumnName.PORT.toString()),
                    rs.getString(HWProjectColumnName.SERVER_NAME.toString()),
                    rs.getString(HWProjectColumnName.USER_NAME.toString()),
                    rs.getString(HWProjectColumnName.PASSWORD.toString())
            );
            builder.description(rs.getString(HWProjectColumnName.DESCRIPTION.toString()));
            return builder.build();
        }
    }

    private SelectorInstanceInfo createSelectorInstanceInfo (){
        SelectorInstanceInfo selector = new SelectorInstanceInfo();
        return  selector;
    }

    private SelectorSizeForTablespace createSelectorSizeForTablespace (){
        SelectorSizeForTablespace selector = new SelectorSizeForTablespace();
        return  selector;
    }

    private SelectorSizeForIndexLob createSelectorSizeForIndexLob (String segment){
        SelectorSizeForIndexLob selector = new SelectorSizeForIndexLob();
        selector.setSegment(segment);
        return  selector;
    }

    private SelectorLastErrors createSelectorLastErrors (){
        SelectorLastErrors selector = new SelectorLastErrors();
        return  selector;
    }

    private SelectorActiveSessions createSelectorActiveSessions (int top){
        SelectorActiveSessions selector = new SelectorActiveSessions();
        selector.setTop(top);
        return  selector;
    }

    private SelectorActiveQueries createSelectorActiveQueries (int top){
        SelectorActiveQueries selector = new SelectorActiveQueries();
        selector.setTop(top);
        return  selector;
    }

    private SelectorQueriesResults createSelectorQueriesResults (int top){
        SelectorQueriesResults selector = new SelectorQueriesResults();
        selector.setTop(top);
        return  selector;
    }

    private SelectorSqlQueryMonitor createSelectorSqlQueryMonitor (int top){
        SelectorSqlQueryMonitor selector = new SelectorSqlQueryMonitor();
        selector.setTop(top);
        return  selector;
    }

    private SelectorDBLocks createSelectorDBLocks (){
        SelectorDBLocks selector = new SelectorDBLocks();
        return  selector;
    }

    private SelectorActiveJobs createSelectorActiveJobs (int hourCount){
        SelectorActiveJobs selector = new SelectorActiveJobs();
        selector.setHourCount(hourCount);
        return  selector;
    }

    private String INSERT_HM_PROJECT = "call insert_hm_project(?,?,?)";
}
