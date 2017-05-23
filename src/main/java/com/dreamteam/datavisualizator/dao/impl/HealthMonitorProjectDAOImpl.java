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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
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
            SimpleJdbcCall simpleCallTemplateInstanceInfo = new SimpleJdbcCall(generalTemplate);
            SimpleJdbcCall simpleCallTemplateSizeForTablespace = new SimpleJdbcCall(generalTemplate);
            SimpleJdbcCall simpleCallTemplateSizeForIndexLob = new SimpleJdbcCall(generalTemplate);
            SimpleJdbcCall simpleCallTemplateLastErrors = new SimpleJdbcCall(generalTemplate);
            SimpleJdbcCall simpleCallTemplateActiveSessions = new SimpleJdbcCall(generalTemplate);
            SimpleJdbcCall simpleCallTemplateActiveQueries = new SimpleJdbcCall(generalTemplate);
            SimpleJdbcCall simpleCallTemplateQueriesResults = new SimpleJdbcCall(generalTemplate);
            SimpleJdbcCall simpleCallTemplateSqlQueryMonitor = new SimpleJdbcCall(generalTemplate);
            SimpleJdbcCall simpleCallTemplateDBLocks = new SimpleJdbcCall(generalTemplate);
            SimpleJdbcCall simpleCallTemplateActiveJobs = new SimpleJdbcCall(generalTemplate);

            createSelectorInstanceInfo(simpleCallTemplateInstanceInfo);
            createSelectorSizeForTablespace(simpleCallTemplateSizeForTablespace);
            createSelectorSizeForIndexLob(simpleCallTemplateSizeForIndexLob, "dba_extents");
            createSelectorLastErrors(simpleCallTemplateLastErrors);
            createSelectorActiveSessions(simpleCallTemplateActiveSessions, 10);
            createSelectorActiveQueries(simpleCallTemplateActiveQueries, 10);
            createSelectorQueriesResults(simpleCallTemplateQueriesResults, 10);
            createSelectorSqlQueryMonitor(simpleCallTemplateSqlQueryMonitor,10);
            createSelectorDBLocks(simpleCallTemplateDBLocks);
            createSelectorActiveJobs(simpleCallTemplateActiveJobs, 2);
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

    private SelectorInstanceInfo createSelectorInstanceInfo(SimpleJdbcCall simpleCallTemplateInstanceInfo) {
        SelectorInstanceInfo selector = new SelectorInstanceInfo();
        simpleCallTemplateInstanceInfo.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_INSTANCE_INFO);
        String sql_query = simpleCallTemplateInstanceInfo.executeFunction(String.class).toString();
        return selector;
    }

    private SelectorSizeForTablespace createSelectorSizeForTablespace(SimpleJdbcCall simpleCallTemplateSizeForTablespace) {
        SelectorSizeForTablespace selector = new SelectorSizeForTablespace();
        simpleCallTemplateSizeForTablespace.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_SIZE_TABLESPACE);
        String sql_query = simpleCallTemplateSizeForTablespace.executeFunction(String.class).toString();
        return selector;
    }

    private SelectorSizeForIndexLob createSelectorSizeForIndexLob(SimpleJdbcCall simpleCallTemplateSizeForIndexLob, String segment) {
        SelectorSizeForIndexLob selector = new SelectorSizeForIndexLob();
        selector.setSegment(segment);
        simpleCallTemplateSizeForIndexLob.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_SIZE_INDEX_LOB);
        SqlParameterSource in = new MapSqlParameterSource().addValue("segment", segment);
        String sql_query = simpleCallTemplateSizeForIndexLob.executeFunction(String.class, in).toString();
        return selector;
    }

    private SelectorLastErrors createSelectorLastErrors(SimpleJdbcCall simpleCallTemplateLastErrors) {
        SelectorLastErrors selector = new SelectorLastErrors();
        simpleCallTemplateLastErrors.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_LAST_ERRORS);
        String sql_query = simpleCallTemplateLastErrors.executeFunction(String.class).toString();
        return selector;
    }

    private SelectorActiveSessions createSelectorActiveSessions(SimpleJdbcCall simpleCallTemplateActiveSessions, int top) {
        SelectorActiveSessions selector = new SelectorActiveSessions();
        selector.setTop(top);
        simpleCallTemplateActiveSessions.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_ACTIVE_SESSIONS);
        SqlParameterSource in = new MapSqlParameterSource().addValue("top", top);
        String sql_query = simpleCallTemplateActiveSessions.executeFunction(String.class, in).toString();
        return selector;
    }

    private SelectorActiveQueries createSelectorActiveQueries(SimpleJdbcCall simpleCallTemplateActiveQueries, int top) {
        SelectorActiveQueries selector = new SelectorActiveQueries();
        selector.setTop(top);
        simpleCallTemplateActiveQueries.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_ACTIVE_QUERIES);
        SqlParameterSource in = new MapSqlParameterSource().addValue("top", top);
        String sql_query = simpleCallTemplateActiveQueries.executeFunction(String.class, in).toString();
        return selector;
    }

    private SelectorQueriesResults createSelectorQueriesResults(SimpleJdbcCall simpleCallTemplateQueriesResults, int top) {
        SelectorQueriesResults selector = new SelectorQueriesResults();
        selector.setTop(top);
        simpleCallTemplateQueriesResults.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_QUERIES_RESULTS);
        SqlParameterSource in = new MapSqlParameterSource().addValue("top", top);
        String sql_query = simpleCallTemplateQueriesResults.executeFunction(String.class, in).toString();
        return selector;
    }

    private SelectorSqlQueryMonitor createSelectorSqlQueryMonitor(SimpleJdbcCall simpleCallTemplateSqlQueryMonitor, int top) {
        SelectorSqlQueryMonitor selector = new SelectorSqlQueryMonitor();
        selector.setTop(top);
        simpleCallTemplateSqlQueryMonitor.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_SQL_MONITOR);
        SqlParameterSource in = new MapSqlParameterSource().addValue("top", top);
        String sql_query = simpleCallTemplateSqlQueryMonitor.executeFunction(String.class, in).toString();
        return selector;
    }

    private SelectorDBLocks createSelectorDBLocks(SimpleJdbcCall simpleCallTemplateDBLocks) {
        SelectorDBLocks selector = new SelectorDBLocks();
        simpleCallTemplateDBLocks.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_DB_LOCKS);
        String sql_query = simpleCallTemplateDBLocks.executeFunction(String.class).toString();
        return selector;
    }

    private SelectorActiveJobs createSelectorActiveJobs(SimpleJdbcCall simpleCallTemplateActiveJobs, int hourCount) {
        SelectorActiveJobs selector = new SelectorActiveJobs();
        selector.setHourCount(hourCount);
        simpleCallTemplateActiveJobs.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_ACTIVE_JOBS);
        SqlParameterSource in = new MapSqlParameterSource().addValue("hour_count", hourCount);
        String sql_query = simpleCallTemplateActiveJobs.executeFunction(String.class, in).toString();
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
    private static final String SELECT_SIMPLE_SELECTOR_BY_PROJECTID = "select selector.object_id id, selector.name name, selector_value.BIG_VALUE value" +
            " from objects selector, attributes selector_value, objreference selector_ref" +
            " where selector_value.object_id = selector.object_id and selector_value.attr_id = 13" +
            " and selector.object_id = selector_ref.reference and selector_ref.attr_id = 19" +
            " and selector.object_type_id = ? and selector_ref.object_id = ?";
    private static final String SELECT_SELECTOR_SEGMENT_BY_PROJECTID = "select selector.object_id id,selector.name name, selector_value.BIG_VALUE value, selector_segment.value segment" +
            " from objects selector, attributes selector_value, attributes selector_segment, objreference selector_ref" +
            " where selector_value.object_id = selector.object_id and selector_value.attr_id = 13" +
            " and selector_segment.object_id = selector.object_id and selector_segment.attr_id = 14" +
            " and selector.object_id = selector_ref.reference and selector_ref.attr_id = 19" +
            " and selector.object_type_id = ? and selector_ref.object_id = ?";
    private static final String SELECT_SELECTOR_TOP_BY_PROJECTID = "select selector.object_id id,selector.name name, selector_value.BIG_VALUE value, selector_top.value top" +
            " from objects selector, attributes selector_value, attributes selector_top, objreference selector_ref" +
            " where  selector_value.object_id = selector.object_id and selector_value.attr_id = 13" +
            " and selector_top.object_id = selector.object_id and selector_top.attr_id = 15" +
            " and selector.object_id = selector_ref.reference and selector_ref.attr_id = 19" +
            " and selector.object_type_id = ? and selector_ref.object_id = ?";
    private static final String SELECT_SELECTOR_HOUR_COUNT_BY_PROJECTID = "select selector.object_id id,selector.name name, selector_value.BIG_VALUE value, selector_hour_count.value hour_count" +
            " from objects selector, attributes selector_value, attributes selector_hour_count, objreference selector_ref" +
            " where selector_value.object_id = selector.object_id and selector_value.attr_id = 13" +
            " and selector_hour_count.object_id = selector.object_id and selector_hour_count.attr_id = 16" +
            " and selector.object_id = selector_ref.reference and selector_ref.attr_id = 19" +
            " and selector.object_type_id = ? and selector_ref.object_id = ?";

    private static final String SELECTOR_QUERIES_PACKAGE = "pkg_selectors";
    private static final String QUERY_FOR_INSTANCE_INFO = "instance_info";
    private static final String QUERY_FOR_SIZE_TABLESPACE = "size_for_tablespace";
    private static final String QUERY_FOR_SIZE_INDEX_LOB = "size_for_index_lob";
    private static final String QUERY_FOR_LAST_ERRORS = "last_errors";
    private static final String QUERY_FOR_ACTIVE_SESSIONS = "active_sessions";
    private static final String QUERY_FOR_ACTIVE_QUERIES = "active_queries";
    private static final String QUERY_FOR_QUERIES_RESULTS = "queries_results";
    private static final String QUERY_FOR_SQL_MONITOR = "sql_query_monitor";
    private static final String QUERY_FOR_DB_LOCKS = "db_locks";
    private static final String QUERY_FOR_ACTIVE_JOBS = "active_jobs";

    private enum HWProjectColumnName {
        id("id"),
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
        json("json"),
        value("value"),
        segment("segment"),
        top("top");

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

    private class SelectorInstanceInfoRowMapper implements RowMapper<SelectorInstanceInfo> {
        public SelectorInstanceInfo mapRow(ResultSet rs, int rownum) throws SQLException {
            SelectorInstanceInfo selector = new SelectorInstanceInfo();
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String value = rs.getString(HWProjectColumnName.value.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());
            selector.setId(id);
            selector.setName(name);
            selector.setValue(value);
            return selector;
        }
    }

    private class SelectorSizeForTablespaceRowMapper implements RowMapper<SelectorSizeForTablespace> {
        public SelectorSizeForTablespace mapRow(ResultSet rs, int rownum) throws SQLException {
                SelectorSizeForTablespace selector = new SelectorSizeForTablespace();
                BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
                String value = rs.getString(HWProjectColumnName.value.toString());
                String name = rs.getString(HWProjectColumnName.name.toString());
                selector.setId(id);
                selector.setName(name);
                selector.setValue(value);
                return selector;
        }
    }

    private class SelectorSizeForIndexLobRowMapper implements RowMapper<SelectorSizeForIndexLob> {
        public SelectorSizeForIndexLob mapRow(ResultSet rs, int rownum) throws SQLException {
            SelectorSizeForIndexLob selector = new SelectorSizeForIndexLob();
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String value = rs.getString(HWProjectColumnName.value.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());
            String segment = rs.getString(HWProjectColumnName.segment.toString());
            selector.setId(id);
            selector.setName(name);
            selector.setValue(value);
            selector.setSegment(segment);
            return selector;
        }
    }

    private class SelectorLastErrorsRowMapper implements RowMapper<SelectorLastErrors> {
        public SelectorLastErrors mapRow(ResultSet rs, int rownum) throws SQLException {
            SelectorLastErrors selector = new SelectorLastErrors();
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String value = rs.getString(HWProjectColumnName.value.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());
            selector.setId(id);
            selector.setName(name);
            selector.setValue(value);
            return selector;
        }
    }

    private class SelectorActiveSessionsRowMapper implements RowMapper<SelectorActiveSessions> {
        public SelectorActiveSessions mapRow(ResultSet rs, int rownum) throws SQLException {
            SelectorActiveSessions selector = new SelectorActiveSessions();
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String value = rs.getString(HWProjectColumnName.value.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());
            int top = rs.getInt(HWProjectColumnName.top.toString());
            selector.setId(id);
            selector.setName(name);
            selector.setValue(value);
            selector.setTop(top);
            return selector;
        }
    }

    private class SelectorActiveQueriesRowMapper implements RowMapper<SelectorActiveQueries> {
        public SelectorActiveQueries mapRow(ResultSet rs, int rownum) throws SQLException {
            SelectorActiveQueries selector = new SelectorActiveQueries();
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String value = rs.getString(HWProjectColumnName.value.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());
            int top = rs.getInt(HWProjectColumnName.top.toString());
            selector.setId(id);
            selector.setName(name);
            selector.setValue(value);
            selector.setTop(top);
            return selector;
        }
    }

    private class SelectorQueriesResultsRowMapper implements RowMapper<SelectorQueriesResults> {
        public SelectorQueriesResults mapRow(ResultSet rs, int rownum) throws SQLException {
            SelectorQueriesResults selector = new SelectorQueriesResults();
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String value = rs.getString(HWProjectColumnName.value.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());
            int top = rs.getInt(HWProjectColumnName.top.toString());
            selector.setId(id);
            selector.setName(name);
            selector.setValue(value);
            selector.setTop(top);
            return selector;
        }
    }

    private class SelectorSqlQueryMonitorRowMapper implements RowMapper<SelectorSqlQueryMonitor> {
        public SelectorSqlQueryMonitor mapRow(ResultSet rs, int rownum) throws SQLException {
            SelectorSqlQueryMonitor selector = new SelectorSqlQueryMonitor();
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String value = rs.getString(HWProjectColumnName.value.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());
            int top = rs.getInt(HWProjectColumnName.top.toString());
            selector.setId(id);
            selector.setName(name);
            selector.setValue(value);
            selector.setTop(top);
            return selector;
        }
    }

    private class SelectorDBLocksRowMapper implements RowMapper<SelectorDBLocks> {
        public SelectorDBLocks mapRow(ResultSet rs, int rownum) throws SQLException {
            SelectorDBLocks selector = new SelectorDBLocks();
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String value = rs.getString(HWProjectColumnName.value.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());
            selector.setId(id);
            selector.setName(name);
            selector.setValue(value);
            return selector;
        }
    }

    private class SelectorActiveJobsRowMapper implements RowMapper<SelectorActiveJobs> {
        public SelectorActiveJobs mapRow(ResultSet rs, int rownum) throws SQLException {
            SelectorActiveJobs selector = new SelectorActiveJobs();
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            String value = rs.getString(HWProjectColumnName.value.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());
            int hourCount = rs.getInt(HWProjectColumnName.hourCount.toString());
            selector.setId(id);
            selector.setName(name);
            selector.setValue(value);
            selector.setHourCount(hourCount);
            return selector;
        }
    }
}
