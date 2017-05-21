package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.common.configurations.HMDataSource;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.*;
import com.dreamteam.datavisualizator.services.ClobToStringService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.dreamteam.datavisualizator.common.IdList.HEALTH_MONITOR_PROJECT_OBJTYPE_ID;

@Repository
public class HealthMonitorProjectDAOImpl extends AbstractDAO implements HealthMonitorProjectDAO {
    private static final Logger LOGGER = Logger.getLogger(HealthMonitorProjectDAOImpl.class);

    private JdbcTemplate templateHM;

    @Autowired
    private JdbcTemplate generalTemplate;

    @Autowired
    private SimpleJdbcCall simpleCallTemplate;

    public void setDataSourceTemplate(String serverName, String port, String sid, String username, String password) {
        String url = "jdbc:oracle:thin:@" + serverName + ":" + port + "/" + sid;
        HMDataSource dataSourceHM = new HMDataSource(url, username, password);
        templateHM = new JdbcTemplate(dataSourceHM.createDataSource());
    }

    public Graphic getProjectGraph(Project project) {
        try {
            return generalTemplate.queryForObject(SELECT_PROJECT_GRAPH, new Object[]{project.getId()}, new GraphicHMRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Graph not selected");
            return null;
        }
    }

    public List<Selector> getProjectSelectors(Project project) {
        try {
            //TODO method for fetching buildSelectors
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Selectors not fetched");
            return null;
        }
    }

    public Map<String, String> getProjectConnections(Project project) {
        try {
            //TODO method for fetching connections of project
            return null;
        } catch (Exception e) {
            LOGGER.error("Connections not fetched");
            return null;
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public Graphic createGraph(int hourCount) {
        try {
            //TODO method for saving buildGraphic
            return null;
        } catch (Exception e) {
            LOGGER.error("Graph not created", e);
            return null;
        }
    }

    public List<Selector> createSelectorList(Map<String, String> map) {
        try {
            //TODO method for saving buildSelectors
            return null;
        } catch (Exception e) {
            LOGGER.error("List of buildSelectors not created", e);
            return null;
        }
    }

    //public boolean saveGraphic(int hourCount, Graphic buildGraphic) { return false;  }
    //public boolean saveSelector(Map<String, String> map, Selector selector) {return false;}

    public Project getProjectById(BigInteger id) {
        try {
            return generalTemplate.queryForObject(SELECT_HMPROJECT_BY_ID, new Object[]{id}, new HealthMonitorProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched", e);
            return null;
        }
    }

    public Project getProjectByName(String projectName) {
        try {
            return generalTemplate.queryForObject(SELECT_HMPROJECT_BY_NAME, new Object[]{projectName}, new HealthMonitorProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched", e);
            return null;
        }
    }

    public List<HealthMonitorProject> getProjectsByAuthor(User user) {
        try {
            return generalTemplate.query(SELECT_HMPROJECTS_BY_AUTHOR, new Object[]{user.getId()}, new HealthMonitorProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Projects not fetched");
            return null;
        }
    }

    public List<HealthMonitorProject> getProjectsUserHaveAccessTo(User user) {
        try {
            return generalTemplate.query(SELECT_HMPROJECTS_USER_HAVE_ACCESS_TO, new Object[]{user.getId()}, new HealthMonitorProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Projects not fetched", e);
            return null;
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean deleteProject(Project project) {
        try {
            return false;
        } catch (DataAccessException e) {
            LOGGER.error("Project not deleted", e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Project not deleted", e);
            return false;
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean saveProject(Project project) {
        try {
            //TODO method for saving of a HM project
            BigInteger projectId = createObject(project.getName(), HEALTH_MONITOR_PROJECT_OBJTYPE_ID);
//        project.setId(createHMProjectObject(project.getName()));
//        generalTemplate.update(INSERT_USER_ATTR_VALUE, USER_EMAIL_ATTR_ID, insertedObjectId, email);
//        generalTemplate.update(INSERT_HM_PROJECT, project.getName(), project.getDescription(), project.getAuthor().toString());
        } catch (DataAccessException e) {
            LOGGER.error("Project not saved", e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Project not saved", e);
            return false;
        }
        return true;
    }

    private SelectorInstanceInfo createSelectorInstanceInfo() {
        SelectorInstanceInfo selector = new SelectorInstanceInfo();
        return selector;
    }

    private SelectorSizeForTablespace createSelectorSizeForTablespace() {
        SelectorSizeForTablespace selector = new SelectorSizeForTablespace();
        return selector;
    }

    private SelectorSizeForIndexLob createSelectorSizeForIndexLob(String segment) {
        SelectorSizeForIndexLob selector = new SelectorSizeForIndexLob();
        selector.setSegment(segment);
        return selector;
    }

    private SelectorLastErrors createSelectorLastErrors() {
        SelectorLastErrors selector = new SelectorLastErrors();
        return selector;
    }

    private SelectorActiveSessions createSelectorActiveSessions(int top) {
        SelectorActiveSessions selector = new SelectorActiveSessions();
        selector.setTop(top);
        return selector;
    }

    private SelectorActiveQueries createSelectorActiveQueries(int top) {
        SelectorActiveQueries selector = new SelectorActiveQueries();
        selector.setTop(top);
        return selector;
    }

    private SelectorQueriesResults createSelectorQueriesResults(int top) {
        SelectorQueriesResults selector = new SelectorQueriesResults();
        selector.setTop(top);
        return selector;
    }

    private SelectorSqlQueryMonitor createSelectorSqlQueryMonitor(int top) {
        SelectorSqlQueryMonitor selector = new SelectorSqlQueryMonitor();
        selector.setTop(top);
        return selector;
    }

    private SelectorDBLocks createSelectorDBLocks() {
        SelectorDBLocks selector = new SelectorDBLocks();
        return selector;
    }

    private SelectorActiveJobs createSelectorActiveJobs(int hourCount) {
        SelectorActiveJobs selector = new SelectorActiveJobs();
        selector.setHourCount(hourCount);
        return selector;
    }


    private static final String SELECT_HMPROJECTS_BY_AUTHOR = "SELECT hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date, author.object_id author, description.value description" +
            " FROM objects hmproject, attributes creation_date, objects author, attributes description, objreference ref" +
            " WHERE hmproject.object_type_id = 3" +
            " AND hmproject.object_id = creation_date.object_id" +
            " AND creation_date.attr_id = 6" +
            " AND hmproject.object_id = description.object_id" +
            " AND description.attr_id = 7" +
            " AND ref.attr_id = 17" +
            " AND ref.object_id = hmproject.object_id" +
            " AND ref.reference = author.object_id" +
            " AND author.object_id=?" +
            " ORDER BY creation_date.date_value";
    private static final String SELECT_HMPROJECTS_USER_HAVE_ACCESS_TO = "SELECT hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date, author.object_id author, description.value description" +
            " FROM objects hmproject, attributes creation_date, objects have_access, attributes description, objects author, objreference ref_author, objreference ref_access" +
            " WHERE hmproject.object_type_id = 3" +
            " AND hmproject.object_id = creation_date.object_id" +
            " AND creation_date.attr_id = 6" +
            " AND hmproject.object_id = description.object_id" +
            " AND description.attr_id = 7" +
            " AND ref_author.attr_id = 17" +
            " AND ref_author.object_id = hmproject.object_id" +
            " AND ref_author.reference = author.object_id" +
            " AND ref_access.attr_id = 18" +
            " AND ref_access.object_id = hmproject.object_id" +
            " AND ref_access.reference = have_access.object_id" +
            " AND have_access.object_id = ?" +
            " ORDER BY creation_date.date_value";
    private static final String SELECT_PROJECT_GRAPH = "SELECT graph.object_id id, graph.name name, json.big_value json, hour_count.value hour_count" +
            " FROM objects graph, objects project, attributes json, attributes hour_count, objreference reference" +
            " WHERE graph.object_id = json.object_id" +
            " AND json.attr_id = 13" +
            " AND graph.object_id = hour_count.object_id" +
            " AND hour_count.attr_id = 16" +
            " AND reference.attr_id = 20" +
            " AND reference.reference = graph.object_id" +
            " AND reference.object_id = project.object_id" +
            " AND project.object_id = ?";
    private static final String SELECT_HMPROJECT_BY_ID = "SELECT hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date,author.object_id author,description.value description," +
            " sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password" +
            " FROM objects hmproject, attributes creation_date,objects author, attributes description, attributes sid," +
            " attributes port, attributes server_name, attributes user_name, attributes password, objreference ref " +
            " WHERE hmproject.object_type_id = 3" +
            " AND hmproject.object_id = creation_date.object_id" +
            " AND creation_date.attr_id = 6" +
            " AND hmproject.object_id = description.object_id" +
            " AND description.attr_id = 7" +
            " AND hmproject.object_id = sid.object_id" +
            " AND sid.attr_id = 25" +
            " AND hmproject.object_id = port.object_id" +
            " AND port.attr_id = 24" +
            " AND hmproject.object_id =  server_name.object_id" +
            " AND server_name.attr_id = 23" +
            " AND hmproject.object_id = user_name.object_id" +
            " AND user_name.attr_id = 26" +
            " AND hmproject.object_id = password.object_id" +
            " AND password.attr_id = 4" +
            " AND ref.attr_id = 17" +
            " AND ref.object_id = hmproject.object_id" +
            " AND ref.reference = author.object_id" +
            " AND hmproject.object_id = ? ";
    private static final String SELECT_HMPROJECT_BY_NAME = "SELECT hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date,author.object_id author,description.value description," +
            " sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password" +
            " FROM objects hmproject, attributes creation_date,objects author, attributes description, attributes sid," +
            " attributes port, attributes server_name, attributes user_name, attributes password, objreference ref " +
            " WHERE hmproject.object_type_id = 3" +
            " AND hmproject.object_id = creation_date.object_id" +
            " AND creation_date.attr_id = 6" +
            " AND hmproject.object_id = description.object_id" +
            " AND description.attr_id = 7" +
            " AND hmproject.object_id = sid.object_id" +
            " AND sid.attr_id = 25" +
            " AND hmproject.object_id = port.object_id" +
            " AND port.attr_id = 24" +
            " AND hmproject.object_id =  server_name.object_id" +
            " AND server_name.attr_id = 23" +
            " AND hmproject.object_id = user_name.object_id" +
            " AND user_name.attr_id = 26" +
            " AND hmproject.object_id = password.object_id" +
            " AND password.attr_id = 4" +
            " AND ref.attr_id = 17" +
            " AND ref.object_id = hmproject.object_id" +
            " AND ref.reference = author.object_id" +
            " AND hmproject.name = ? ";
    private static final String SELECT_SELECTOR_INSTANCE_INFO = "select BANNER oracle_version," +
            " sys_context('userenv', 'DB_NAME') db_name," +
            " sys_context('userenv','CURRENT_SCHEMA') schem_name" +
            " from v$version where rownum=1";


    private enum HWProjectColumnName {
        id("buildId"),
        name("name"),
        createDate("creation_date"),
        author("author"),
        description("description"),
        sid("sid"),
        port("port"),
        serverName("server_name"),
        userName("user_name"),
        password("password"),
        hourCount("hour_count"),
        json("json");

        private final String columnName;

        HWProjectColumnName(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private class HealthMonitorProjectRowMapper implements RowMapper<HealthMonitorProject> {
        public HealthMonitorProject mapRow(ResultSet rs, int rownum) throws SQLException {
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String name = rs.getString(HWProjectColumnName.name.toString());
            Date creationDate = rs.getDate(HWProjectColumnName.createDate.toString());
            String description = rs.getString(HWProjectColumnName.description.toString());
            BigInteger author = BigInteger.valueOf(rs.getLong(HWProjectColumnName.author.toString()));
            String sid = rs.getString(HWProjectColumnName.sid.toString());
            String port = rs.getString(HWProjectColumnName.port.toString());
            String serverName = rs.getString(HWProjectColumnName.serverName.toString());
            String userName = rs.getString(HWProjectColumnName.userName.toString());
            String password = rs.getString(HWProjectColumnName.password.toString());

            HealthMonitorProject.Builder builder = new HealthMonitorProject.Builder(id, name, creationDate, description, author, sid, port, serverName, userName, password);
            // builder.buildDescription(rs.getString(HWProjectColumnName.DESCRIPTION.toString()));
            return builder.buildProject();
        }
    }

    private class GraphicHMRowMapper implements RowMapper<GraphicHMImpl> {
        public GraphicHMImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
            BigInteger id = null;
            Integer hourCount = null;
            String name = null;

            GraphicHMImpl.HMGraphBuilder builder = new GraphicHMImpl.HMGraphBuilder();
            builder.buildId(BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString())));
            builder.buildHourCount(rs.getInt(HWProjectColumnName.hourCount.toString()));
            builder.buildName(rs.getString(HWProjectColumnName.name.toString()));

            Clob graphicInClobType = rs.getClob(HWProjectColumnName.json.toString());
            LOGGER.info("clob = " + graphicInClobType);
            String clobString = ClobToStringService.clobToString(graphicInClobType);
            LOGGER.info("clobString = " + clobString);
            //JsonObject jsonObj = new JsonObject().getAsJsonObject(clobString);
            JsonObject graphicInJsonType = new JsonParser().parse(clobString).getAsJsonObject();
            LOGGER.info("jsonObj = " + graphicInJsonType);

            builder.buildGraphicJson(graphicInJsonType);
            return builder.buildGraphic();
        }
    }
}
