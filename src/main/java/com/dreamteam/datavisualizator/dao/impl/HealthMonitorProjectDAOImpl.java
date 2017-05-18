package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.common.IdList;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Repository
public class HealthMonitorProjectDAOImpl implements HealthMonitorProjectDAO {
    private JdbcTemplate templateHM;


    private enum HWProjectColumnName {
        id("id"),
        name("name"),
        createDate("create_date"),
        author("author"),
        description("description"),
        sid("sid"),
        port("port"),
        serverName("server_name"),
        userName("user_name"),
        password("password");
        private final String columnName;

        private HWProjectColumnName(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }



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

    public HealthMonitorProject getProjectById(BigInteger id) {
        return generalTemplate.queryForObject(SELECT_HMPROJECT_BY_ID, new Object[]{id}, new HealthMonitorProjectRowMapper());
    }

    public Project getProjectByName(String projectName) {
        return generalTemplate.queryForObject(SELECT_HMPROJECT_BY_NAME, new Object[]{projectName}, new HealthMonitorProjectRowMapper());
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

    private class HealthMonitorProjectRowMapper implements RowMapper<HealthMonitorProject> {
        public HealthMonitorProject mapRow(ResultSet rs, int rownum) throws SQLException {
            HealthMonitorProject.Builder builder = new HealthMonitorProject.Builder(
                    BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString())),
                    rs.getString(HWProjectColumnName.name.toString()),
                    rs.getDate(HWProjectColumnName.createDate.toString()),
                    BigInteger.valueOf(rs.getLong(HWProjectColumnName.author.toString())),
                    rs.getString(HWProjectColumnName.sid.toString()),
                    rs.getString(HWProjectColumnName.port.toString()),
                    rs.getString(HWProjectColumnName.serverName.toString()),
                    rs.getString(HWProjectColumnName.userName.toString()),
                    rs.getString(HWProjectColumnName.password.toString())
            );
            // builder.description(rs.getString(HWProjectColumnName.DESCRIPTION.toString()));
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
    private static final String SELECT_HMPROJECTS_BY_AUTHOR = "select objects.object_id id, objects.name name, creation_date.date_value creation_date," +
            " description.value description" +
            " from OBJECTS, ATTRIBUTES creation_date,Objects author, ATTRIBUTES description, OBJREFERENCE ref" +
            " WHERE OBJECTS.OBJECT_ID=creation_date.object_id" +
            " AND creation_date.ATTR_id=" + IdList.PROJECT_DATE_ATTR_ID +
            " and OBJECTS.OBJECT_ID=description.object_id" +
            " and description.ATTR_ID=" + IdList.PROJECT_DESCRIPTION_ATTR_ID +
            " and ref.ATTR_ID=" + IdList.PROJECT_AUTHOR_RELATION_ATTR_ID +
            " and ref.OBJECT_ID=OBJECTS.OBJECT_ID" +
            " and ref.REFERENCE=author.OBJECT_ID" +
            " and author.OBJECT_ID=?"+
            " and OBJECTS.OBJECT_TYPE_ID="+ IdList.HEALTH_MONITOR_PROJECT_OBJTYPE_ID+
            " ORDER BY creation_date.date_value";
    private static final String SELECT_HMPROJECTS_USER_HAVE_ACCESS_TO = "select objects.object_id id, objects.name name, creation_date.date_value creation_date,author.object_id author," +
            " description.value description" +
            " from OBJECTS, ATTRIBUTES creation_date,Objects author, ATTRIBUTES description, OBJREFERENCE ref" +
            " WHERE OBJECTS.OBJECT_ID=creation_date.object_id" +
            " AND creation_date.ATTR_id=" + IdList.PROJECT_DATE_ATTR_ID +
            " and OBJECTS.OBJECT_ID=description.object_id" +
            " and description.ATTR_ID=" + IdList.PROJECT_DESCRIPTION_ATTR_ID +
            " and ref.ATTR_ID=" + IdList.PROJECT_SHARED_RELATION_ATTR_ID +
            " and ref.OBJECT_ID=OBJECTS.OBJECT_ID" +
            " and ref.REFERENCE=author.OBJECT_ID" +
            " and author.OBJECT_ID=?"+
            " and OBJECTS.OBJECT_TYPE_ID="+ IdList.HEALTH_MONITOR_PROJECT_OBJTYPE_ID+
            " ORDER BY creation_date.date_value";;
    private static final String SELECT_PROJECT_GRAPH = "select graph.object_id id, graph.name name, json.value json" +
            " from OBJECTS graph,OBJECTS project, ATTRIBUTES json, OBJREFERENCE reference" +
            " where graph.OBJECT_ID=json.OBJECT_ID" +
            " and json.ATTR_ID=" + IdList.JSON_RESULT_ATTR_ID +
            " and reference.ATTR_ID=" + IdList.PROJECT_GRAPHICS_RELATION_ATTR_ID +
            " and reference.REFERENCE=graph.OBJECT_ID" +
            " and reference.OBJECT_ID=project.OBJECT_ID" +
            " and project.OBJECT_ID=?";
    private static final String SELECT_HMPROJECT_BY_ID = "select objects.object_id id, objects.name name, creation_date.date_value creation_date,author.object_id author,description.value description," +
            " sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password" +
            " from OBJECTS, ATTRIBUTES creation_date,Objects author, ATTRIBUTES description, ATTRIBUTES sid," +
            " ATTRIBUTES port, ATTRIBUTES server_name, ATTRIBUTES user_name, ATTRIBUTES password" +
            " WHERE OBJECTS.OBJECT_ID=creation_date.object_id" +
            " AND creation_date.ATTR_id=" + IdList.PROJECT_DATE_ATTR_ID +
            " and OBJECTS.OBJECT_ID=description.object_id" +
            " and description.ATTR_ID=" + IdList.PROJECT_DESCRIPTION_ATTR_ID +
            " and OBJECTS.OBJECT_ID=sid.OBJECT_ID" +
            " and sid.ATTR_ID=25" + IdList.HM_SID_ATTR_ID +
            " and OBJECTS.OBJECT_ID=port.OBJECT_ID" +
            " and port.ATTR_ID=" + IdList.HM_PORT_ATTR_ID +
            " and OBJECTS.OBJECT_ID=server_name.OBJECT_ID" +
            " and server_name.ATTR_ID=" + IdList.HM_SERVER_NAME_ATTR_ID +
            " and OBJECTS.OBJECT_ID=user_name.OBJECT_ID" +
            " and user_name.ATTR_ID=" + IdList.HM_USER_NAME_ATTR_ID +
            " and OBJECTS.OBJECT_ID=password.OBJECT_ID" +
            " and password.ATTR_ID=" + IdList.PASSWORD_ATTR_ID +
            " and ref.ATTR_ID=" + IdList.PROJECT_AUTHOR_RELATION_ATTR_ID +
            " and ref.OBJECT_ID=OBJECTS.OBJECT_ID" +
            " and ref.REFERENCE=author.OBJECT_ID" +
            " and objects.object_id=?";
    private String SELECT_HMPROJECT_BY_NAME = "select objects.object_id id, objects.name name, creation_date.date_value creation_date,author.object_id author,description.value description," +
            " sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password" +
            " from OBJECTS, ATTRIBUTES creation_date,Objects author, ATTRIBUTES description, ATTRIBUTES sid," +
            " ATTRIBUTES port, ATTRIBUTES server_name, ATTRIBUTES user_name, ATTRIBUTES password" +
            " WHERE OBJECTS.OBJECT_ID=creation_date.object_id" +
            " AND creation_date.ATTR_id=" + IdList.PROJECT_DATE_ATTR_ID +
            " and OBJECTS.OBJECT_ID=description.object_id" +
            " and description.ATTR_ID=" + IdList.PROJECT_DESCRIPTION_ATTR_ID +
            " and OBJECTS.OBJECT_ID=sid.OBJECT_ID" +
            " and sid.ATTR_ID=25" + IdList.HM_SID_ATTR_ID +
            " and OBJECTS.OBJECT_ID=port.OBJECT_ID" +
            " and port.ATTR_ID=" + IdList.HM_PORT_ATTR_ID +
            " and OBJECTS.OBJECT_ID=server_name.OBJECT_ID" +
            " and server_name.ATTR_ID=" + IdList.HM_SERVER_NAME_ATTR_ID +
            " and OBJECTS.OBJECT_ID=user_name.OBJECT_ID" +
            " and user_name.ATTR_ID=" + IdList.HM_USER_NAME_ATTR_ID +
            " and OBJECTS.OBJECT_ID=password.OBJECT_ID" +
            " and password.ATTR_ID=" + IdList.PASSWORD_ATTR_ID +
            " and ref.ATTR_ID=" + IdList.PROJECT_AUTHOR_RELATION_ATTR_ID +
            " and ref.OBJECT_ID=OBJECTS.OBJECT_ID" +
            " and ref.REFERENCE=author.OBJECT_ID" +
            " and objects.name=?";

    private static final String SELECT_SELECTOR_INSTANCE_INFO = "select BANNER oracle_version, \n" +
            "sys_context('userenv', 'DB_NAME') db_name,\n" +
            "sys_context('userenv','CURRENT_SCHEMA') schem_name\n" +
            "from v$version where rownum=1";

}
