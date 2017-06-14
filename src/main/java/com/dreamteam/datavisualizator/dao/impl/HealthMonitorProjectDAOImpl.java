package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.common.IdList;
import com.dreamteam.datavisualizator.common.configurations.HMDataSource;
import com.dreamteam.datavisualizator.common.exceptions.HMConnectionException;
import com.dreamteam.datavisualizator.common.exceptions.HMGraphException;
import com.dreamteam.datavisualizator.common.exceptions.HMGraphSerializerException;
import com.dreamteam.datavisualizator.common.exceptions.SelectorCreateException;
import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.common.selectors.impl.*;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.*;
import com.dreamteam.datavisualizator.services.ClobToStringService;
import com.dreamteam.datavisualizator.services.HmGraphSerializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.dreamteam.datavisualizator.common.IdList.*;

@Repository
public class HealthMonitorProjectDAOImpl extends AbstractDAO implements HealthMonitorProjectDAO {
    private static final Logger LOGGER = Logger.getLogger(HealthMonitorProjectDAOImpl.class);

    private JdbcTemplate templateHM;

    @Autowired
    private JdbcTemplate generalTemplate;

    @Autowired
    private SimpleJdbcCall simpleCallTemplate;

    @Autowired
    private UserDAO userDAO;

    @Override
    protected BigInteger createObject(String name, BigInteger objectTypeId) {
        simpleCallTemplate.withFunctionName(INSERT_OBJECT);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("obj_type_id", objectTypeId)
                .addValue("obj_name", name);
        return simpleCallTemplate.executeFunction(BigDecimal.class, in).toBigInteger();
    }

    public void setDataSourceTemplate(String serverName, String port, String sid, String username, String password) {
        String url = "jdbc:oracle:thin:@" + serverName + ":" + port + "/" + sid;
        HMDataSource dataSourceHM = new HMDataSource(url, username, password);
        try {
            templateHM = new JdbcTemplate(dataSourceHM.createDataSource());
            templateHM.queryForList("SELECT 1 FROM DUAL");
        }
        catch (DataAccessException e) {
            LOGGER.error("Connection with parameters SERVER - " + serverName + "; PORT - " + port + "; SID - "
                    + sid + "; USER NAME - " + username + "; PASSWORD - "+ password + " have error: \n", e);
            throw new HMConnectionException("Connection with wrong parameters: SERVER - " + serverName + "; PORT - " + port + "; SID - "
                    + sid + "; USER NAME - " + username + "; PASSWORD - "+ password + ".");
        }catch (Exception e) {
            LOGGER.error("Connection with parameters SERVER - " + serverName + "; PORT - " + port + "; SID - "
                    + sid + "; USER NAME - " + username + "; PASSWORD - "+ password + " have error: \n", e);
            throw new HMConnectionException("Connection with wrong parameters: SERVER - " + serverName + "; PORT - " + port + "; SID - "
                    + sid + "; USER NAME - " + username + "; PASSWORD - "+ password + ".");
        }
    }

    public Graphic getProjectGraph(Project project) {
        try {
            if (project != null) {
                return generalTemplate.queryForObject(SELECT_PROJECT_GRAPH, new Object[]{project.getId()}, new GraphicHMRowMapper());
            }else {
                LOGGER.error("Graph not selected because project is null");
                return null;
            }
        }catch (EmptyResultDataAccessException e){
            //LOGGER.error("Graph is absent for project with id - " + project.getId(), e);
            return null;
        }catch (DataAccessException e) {
            LOGGER.error("Graph not selected for project with id - " + project.getId(), e);
            throw new HMGraphException("Graph not selected for project with id - " + project.getId() +". Error: " + e.getLocalizedMessage());
        }catch (Exception e) {
            LOGGER.error("Graph not selected", e);
            throw new HMGraphException("Graph not selected. Something is wrong....");
        }
    }

    public Map<BigInteger, Selector> getProjectSelectors(Project project) {
        try {
            if (project != null) {
                BigInteger projectId = project.getId();
                List<BigInteger> arraySelectorTypes = generalTemplate.queryForList(SELECT_SELECTORS_TYPE_BY_PROJECTID, new Object[]{projectId}, BigInteger.class);
                Map<BigInteger, Selector> mapSelectors = new HashMap<>();
                    for (BigInteger type : arraySelectorTypes) {
                        mapSelectors.put(type, generalTemplate.queryForObject(mapSelectorsSql.get(type), new Object[]{type, projectId}, mapSelectorsRowMapper.get(type)));
                    }
                if (!mapSelectors.isEmpty()) {
                    return mapSelectors;
                }
                LOGGER.error("Selectors is absent for project");
                throw new SelectorCreateException("Selectors is absent for project");
            }else {
                LOGGER.error("List of selectors not selected because project is null");
                throw new SelectorCreateException("List of selectors not selected because project is null");
            }
        } catch (SelectorCreateException e){
            throw e;
        } catch (EmptyResultDataAccessException e){
            LOGGER.error("Selectors is absent for project", e);
            throw new SelectorCreateException("List of selectors is absent for project");
        }catch (DataAccessException e) {
            LOGGER.error("Selectors not fetched for project with id - " + project.getId(), e);
            throw new SelectorCreateException("Selectors not fetched for project with id - " + project.getId());
        }catch (Exception e) {
            LOGGER.error("Selectors not fetched", e);
            throw new SelectorCreateException("Selectors not fetched. Something is wrong....");
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public Graphic createGraph(int hourCount){
        try {
            SimpleJdbcCall simpleCallTemplate = new SimpleJdbcCall(generalTemplate);
            GraphicHMImpl.HMGraphBuilder builderGraph = new GraphicHMImpl.HMGraphBuilder();
            builderGraph.buildHourCount(hourCount);
            builderGraph.buildName("Time-Tasks");

            simpleCallTemplate.withCatalogName(GRAPHIC_QUERY_PACKAGE).withFunctionName(GRAPHIC_QUERY);
            SqlParameterSource in = new MapSqlParameterSource().addValue("hour_count", hourCount);
            String sql_query = simpleCallTemplate.executeFunction(String.class, in).toString();
            ResultSetWrappingSqlRowSet resultsRow = (ResultSetWrappingSqlRowSet) templateHM.queryForRowSet(sql_query);
            ResultSet results = resultsRow.getResultSet();
            if (results.isBeforeFirst()) {
                JsonObject valueG = HmGraphSerializer.serialiseHmGraph(resultsRow);
                builderGraph.buildGraphicJson(valueG);
            }else{
                JsonObject noData = new JsonObject();
                noData.addProperty("data", "<div class=\"alert alert-info graphic-info-mess\"><strong>Info!</strong> No data in database for graph by last "+hourCount+" hour(s).</div>");
                builderGraph.buildGraphicJson(noData);
            }
            return builderGraph.buildGraphic();
        } catch (HMGraphSerializerException e) {
            LOGGER.error("Graph not created from HM data base. ", e);
            throw new HMGraphException("Graph not created from HM data base. " + e.getLocalizedMessage());

        }catch (DataAccessException e) {
            LOGGER.error("Graph not created from HM data base. ", e);
            throw new HMGraphException("Graph not created from HM data base. " + e.getLocalizedMessage());

        } catch (Exception e) {
            LOGGER.error("Graph not created from HM data base.", e);
            throw new HMGraphException("Graph not created from HM data base. Something is wrong....");
        }
    }

    public Map<BigInteger, Selector> createSelectorList(Map<BigInteger, String> map) {
        try {
            if (map != null) {
                Map<BigInteger, Selector> mapSelectors = new HashMap<>();
                for (Map.Entry<BigInteger, String> entry : map.entrySet()) {
                    BigInteger key = entry.getKey();
                    String value = entry.getValue();
                    SelectorCreator selector = mapSelectorCreators.get(key);
                    selector.addSelector(mapSelectors, generalTemplate, templateHM, value);
                }
                return mapSelectors;
            }else {
                LOGGER.error("List of selectors not created because selector settings is wrong");
                throw new SelectorCreateException("List of selectors not created because selector settings is wrong");
            }
        }catch (SelectorCreateException e){
            LOGGER.error("List of buildSelectors not created", e);
            throw e;
        }
        catch (Exception e) {
            LOGGER.error("List of buildSelectors not created", e);
            throw new SelectorCreateException("List of buildSelectors not created. Something is wrong....");
        }
    }

    public Project getProjectById(BigInteger id) {
        try {
            return generalTemplate.queryForObject(SELECT_HMPROJECT_BY_ID, new Object[]{id}, new CompleteHealthMonitorProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched by id " + id, e);
            return null;
        }catch (Exception e) {
            LOGGER.error("Project not fetched by id " + id, e);
            return null;
        }
    }

    public Project getProjectByName(String projectName) {
        try {
            return generalTemplate.queryForObject(SELECT_HMPROJECT_BY_NAME, new Object[]{projectName}, new CuttedHealthMonitorProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched by name " + projectName, e);
            return null;
        }catch (Exception e) {
            LOGGER.error("Project not fetched by name " + projectName, e);
            return null;
        }
    }

    public List<HealthMonitorProject> getProjectsByAuthor(User user) {
        try {
            if (user != null) {
                return generalTemplate.query(SELECT_HMPROJECTS_BY_AUTHOR, new Object[]{user.getId()}, new CuttedHealthMonitorProjectRowMapper());
            } else {
                LOGGER.error("Projects for author wasn't selected");
                return null;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Projects not fetched by author (id:" + user.getId() + " name:" + user.getFullName() + ")", e);
            return null;
        }catch (Exception e) {
            LOGGER.error("Project not fetched by author", e);
            return null;
        }
    }

    public List<HealthMonitorProject> getProjectsUserHaveAccessTo(User user) {
        try {
            if (user != null) {
                return generalTemplate.query(SELECT_HMPROJECTS_USER_HAVE_ACCESS_TO, new Object[]{user.getId()}, new CuttedHealthMonitorProjectRowMapper());
            } else {
                LOGGER.error("Projects for user wasn't selected");
                return null;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched for user with id - " + user.getId(), e);
            return null;
        }catch (Exception e) {
            LOGGER.error("Project not fetched", e);
            return null;
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean deleteProject(Project project) {
        try {
            if (project != null) {
                Graphic hmGraph = getProjectGraph(project);                   //select project graphic
                if (hmGraph != null) {
                    generalTemplate.update(DELETE_OBJECT, hmGraph.getId());       //delete project graphic
                }
                Map<BigInteger, Selector> selectors = getProjectSelectors(project);      //select all project selectors
                if (selectors != null) {
                    for (Map.Entry<BigInteger, Selector> entry : selectors.entrySet()) {
                        Selector value = entry.getValue();
                        generalTemplate.update(DELETE_OBJECT, value.getId());  // deleting all project selectors
                    }
                }
                generalTemplate.update(DELETE_OBJECT, project.getId());         //deleting project
                return true;
            } else {
                LOGGER.error("Project was not removed because it's null");
                return false;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Project with id " +project.getId() + " not deleted", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Project not deleted", e);
            throw e;
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public Project saveProject(Project project) {
        Date projectCreationDate = new Date();
        try {
            if (project != null) {
                //project
                String sid = ((HealthMonitorProject) project).getSid();
                String port = ((HealthMonitorProject) project).getPort();
                String serverName = ((HealthMonitorProject) project).getServerName();
                String userName = ((HealthMonitorProject) project).getUserName();
                String password = ((HealthMonitorProject) project).getPassword();
                String description = project.getDescription();
                String projectName = project.getName();
                BigInteger authorId = project.getAuthor();
                BigInteger projectId = createObject(projectName, HEALTH_MONITOR_PROJECT_OBJTYPE_ID);

                generalTemplate.update(INSERT_ATTR_VALUE, PROJECT_DESCRIPTION_ATTR_ID, projectId, description);
                generalTemplate.update(INSERT_ATTR_DATE_VALUE, PROJECT_DATE_ATTR_ID, projectId, projectCreationDate);
                generalTemplate.update(INSERT_OBJREFERENCE_RELATION, PROJECT_AUTHOR_RELATION_ATTR_ID, authorId, projectId);
                generalTemplate.update(INSERT_ATTR_VALUE, HM_SID_ATTR_ID, projectId, sid);
                generalTemplate.update(INSERT_ATTR_VALUE, HM_PORT_ATTR_ID, projectId, port);
                generalTemplate.update(INSERT_ATTR_VALUE, HM_SERVER_NAME_ATTR_ID, projectId, serverName);
                generalTemplate.update(INSERT_ATTR_VALUE, HM_USER_NAME_ATTR_ID, projectId, userName);
                generalTemplate.update(INSERT_ATTR_VALUE, PASSWORD_ATTR_ID, projectId, password);

                //graph
                Graphic graph = ((HealthMonitorProject) project).getGraphic();
                if (graph != null) {
                    String graphName = graph.getName();
                    int graphicHourCount = ((GraphicHMImpl) graph).getHourCount();
                    JsonObject graphJSON = graph.getGraphicJSON();

                    BigInteger graphId = createObject(graphName, GRAPHIC_OBJTYPE_ID);
                    generalTemplate.update(INSERT_ATTR_VALUE, HOUR_COUNT_ATTR_ID, graphId, graphicHourCount);
                    generalTemplate.update(INSERT_ATTR_BIG_VALUE, JSON_RESULT_ATTR_ID, graphId, graphJSON.toString());
                    generalTemplate.update(INSERT_OBJREFERENCE_RELATION, PROJECT_GRAPHICS_RELATION_ATTR_ID, graphId, projectId);
                }
                //selectors
                Map<BigInteger, Selector> projectSelectors = ((HealthMonitorProject) project).getSelectors();
                if(projectSelectors != null) {
                    for (Map.Entry<BigInteger, Selector> entry : projectSelectors.entrySet()) {
                        BigInteger key = entry.getKey();
                        Selector selector = entry.getValue();
                        BigInteger selectorId = createObject(selector.getName(), key);
                        String selectorValue = selector.getValue();
                        generalTemplate.update(INSERT_ATTR_BIG_VALUE, JSON_RESULT_ATTR_ID, selectorId, selectorValue);
                        if (mapSelectorsAttr.get(key) != null) {
                            String value = mapSelectorCreators.get(key).getAttrValue(selector);
                            generalTemplate.update(INSERT_ATTR_VALUE, mapSelectorsAttr.get(key), selectorId, value);
                        }
                        generalTemplate.update(INSERT_OBJREFERENCE_RELATION, PROJECT_SELECTORS_RELATION_ATTR_ID, selectorId, projectId);
                    }
                }
                return getProjectById(projectId);
            } else {
                LOGGER.error("Project was not save because it's null");
                return null;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Project with name " +project.getName() + " not saved", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Project not saved", e);
            throw e;
        }
    }

    private static final String SELECT_HMPROJECTS_BY_AUTHOR = "SELECT hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date, author.object_id author,author.name author_name, description.value description, sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password" +
            " FROM objects hmproject, attributes creation_date, objects author, attributes description, objreference ref, attributes sid, attributes port, attributes server_name, attributes user_name, attributes password" +
            " WHERE hmproject.object_type_id = 3" +
            " AND hmproject.object_id = creation_date.object_id" +
            " AND creation_date.attr_id = 6" +
            " AND hmproject.object_id = description.object_id" +
            " AND description.attr_id = 7" +
            " AND hmproject.object_id = sid.object_id" +
            " AND sid.attr_id = 23" +
            " AND hmproject.object_id = port.object_id" +
            " AND port.attr_id = 24" +
            " AND hmproject.object_id = server_name.object_id" +
            " AND server_name.attr_id = 25" +
            " AND hmproject.object_id = user_name.object_id" +
            " AND user_name.attr_id = 26" +
            " AND hmproject.object_id = password.object_id" +
            " AND password.attr_id = 4" +
            " AND ref.attr_id = 17" +
            " AND ref.object_id = hmproject.object_id" +
            " AND ref.reference = author.object_id" +
            " AND author.object_id=?" +
            " ORDER BY creation_date.date_value";
    private static final String SELECT_HMPROJECTS_USER_HAVE_ACCESS_TO = "SELECT hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date, author.object_id author, author.name author_name,description.value description" +
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
    private static final String SELECT_HMPROJECT_BY_ID = "SELECT hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date,author.object_id author,author.name author_name,description.value description," +
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
    private static final String SELECT_HMPROJECT_BY_NAME = "SELECT hmproject.object_id id, hmproject.name name," +
            " creation_date.date_value creation_date,author.object_id author, author.name author_name, description.value description," +
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
    private static final String SELECT_SELECTORS_TYPE_BY_PROJECTID ="select objects.object_type_id   from objreference ref_selector, objects" +
            " where ref_selector.attr_id=19 and objects.object_id=ref_selector.reference and ref_selector.object_id = ?";
    private static final String SELECT_SIMPLE_SELECTOR_BY_PROJECTID = "select selector.object_id id, selector.name name, selector_value.BIG_VALUE value" +
            " from objects selector, attributes selector_value, objreference selector_ref" +
            " where selector_value.object_id = selector.object_id and selector_value.attr_id = 13" +        /*selector value*/
            " and selector.object_id = selector_ref.reference and selector_ref.attr_id = 19" +              /*reference - selectors*/
            " and selector.object_type_id = ? and selector_ref.object_id = ?";
    private static final String SELECT_SELECTOR_SEGMENT_BY_PROJECTID = "select selector.object_id id,selector.name name, selector_value.BIG_VALUE value, selector_segment.value segment" +
            " from objects selector, attributes selector_value, attributes selector_segment, objreference selector_ref" +
            " where selector_value.object_id = selector.object_id and selector_value.attr_id = 13" +         /*selector value*/
            " and selector_segment.object_id = selector.object_id and selector_segment.attr_id = 14" +      /*selector segment*/
            " and selector.object_id = selector_ref.reference and selector_ref.attr_id = 19" +              /*reference - selectors*/
            " and selector.object_type_id = ? and selector_ref.object_id = ?";
    private static final String SELECT_SELECTOR_TOP_BY_PROJECTID = "select selector.object_id id,selector.name name, selector_value.BIG_VALUE value, selector_top.value top" +
            " from objects selector, attributes selector_value, attributes selector_top, objreference selector_ref" +
            " where  selector_value.object_id = selector.object_id and selector_value.attr_id = 13" +          /*selector value*/
            " and selector_top.object_id = selector.object_id and selector_top.attr_id = 15" +                  /*top for selector*/
            " and selector.object_id = selector_ref.reference and selector_ref.attr_id = 19" +                  /*reference - selectors*/
            " and selector.object_type_id = ? and selector_ref.object_id = ?";
    private static final String SELECT_SELECTOR_HOUR_COUNT_BY_PROJECTID = "select selector.object_id id,selector.name name, selector_value.BIG_VALUE value, selector_hour_count.value hour_count" +
            " from objects selector, attributes selector_value, attributes selector_hour_count, objreference selector_ref" +
            " where selector_value.object_id = selector.object_id and selector_value.attr_id = 13" +            /*selector value*/
            " and selector_hour_count.object_id = selector.object_id and selector_hour_count.attr_id = 16" +    /*hour count for selector*/
            " and selector.object_id = selector_ref.reference and selector_ref.attr_id = 19" +                  /*reference - selectors*/
            " and selector.object_type_id = ? and selector_ref.object_id = ?";
    private static final String GRAPHIC_QUERY_PACKAGE = "pkg_selectors";
    private static final String GRAPHIC_QUERY = "graph";

    private static final Map<BigInteger, String> mapSelectorsSql = new HashMap<BigInteger, String>(){{
            put(IdList.S_INSTANCE_INFO_OBJTYPE_ID,  SELECT_SIMPLE_SELECTOR_BY_PROJECTID);
            put(IdList.S_SIZE_TABLESPACE_OBJTYPE_ID,SELECT_SIMPLE_SELECTOR_BY_PROJECTID);
            put(IdList.S_SIZE_INDEX_LOB_OBJTYPE_ID, SELECT_SELECTOR_SEGMENT_BY_PROJECTID);
            put(IdList.S_LAST_ERRORS_OBJTYPE_ID,    SELECT_SIMPLE_SELECTOR_BY_PROJECTID);
            put(IdList.S_ACTIVE_SESSIONS_OBJTYPE_ID,SELECT_SELECTOR_TOP_BY_PROJECTID);
            put(IdList.S_ACTIVE_QUERIES_OBJTYPE_ID, SELECT_SELECTOR_TOP_BY_PROJECTID);
            put(IdList.S_QUERIES_RESULTS_OBJTYPE_ID,SELECT_SELECTOR_TOP_BY_PROJECTID);
            put(IdList.S_SQL_MONITOR_OBJTYPE_ID,    SELECT_SELECTOR_TOP_BY_PROJECTID);
            put(IdList.S_DB_LOCKS_OBJTYPE_ID,       SELECT_SIMPLE_SELECTOR_BY_PROJECTID);
            put(IdList.S_ACTIVE_JOBS_OBJTYPE_ID,    SELECT_SELECTOR_HOUR_COUNT_BY_PROJECTID);
    }};
    private final Map<BigInteger, RowMapper<Selector>> mapSelectorsRowMapper = new HashMap<BigInteger, RowMapper<Selector>>(){{
            put(IdList.S_INSTANCE_INFO_OBJTYPE_ID, new SelectorInstanceInfoRowMapper());
            put(IdList.S_SIZE_TABLESPACE_OBJTYPE_ID, new SelectorSizeForTablespaceRowMapper());
            put(IdList.S_SIZE_INDEX_LOB_OBJTYPE_ID, new SelectorSizeForIndexLobRowMapper());
            put(IdList.S_LAST_ERRORS_OBJTYPE_ID, new SelectorLastErrorsRowMapper());
            put(IdList.S_ACTIVE_SESSIONS_OBJTYPE_ID, new SelectorActiveSessionsRowMapper());
            put(IdList.S_ACTIVE_QUERIES_OBJTYPE_ID, new SelectorActiveQueriesRowMapper());
            put(IdList.S_QUERIES_RESULTS_OBJTYPE_ID, new SelectorQueriesResultsRowMapper());
            put(IdList.S_SQL_MONITOR_OBJTYPE_ID, new SelectorSqlQueryMonitorRowMapper());
            put(IdList.S_DB_LOCKS_OBJTYPE_ID, new SelectorDBLocksRowMapper());
            put(IdList.S_ACTIVE_JOBS_OBJTYPE_ID, new SelectorActiveJobsRowMapper());
    }};
    private static final Map<BigInteger, BigInteger> mapSelectorsAttr = new HashMap<BigInteger, BigInteger>(){{
        put(S_INSTANCE_INFO_OBJTYPE_ID,  null);
        put(S_SIZE_TABLESPACE_OBJTYPE_ID,null);
        put(S_SIZE_INDEX_LOB_OBJTYPE_ID, SEGMENT_ATTR_ID);
        put(S_LAST_ERRORS_OBJTYPE_ID,    null);
        put(S_ACTIVE_SESSIONS_OBJTYPE_ID,TOP_ATTR_ID);
        put(S_ACTIVE_QUERIES_OBJTYPE_ID, TOP_ATTR_ID);
        put(S_QUERIES_RESULTS_OBJTYPE_ID,TOP_ATTR_ID);
        put(S_SQL_MONITOR_OBJTYPE_ID,    TOP_ATTR_ID);
        put(S_DB_LOCKS_OBJTYPE_ID,       null);
        put(S_ACTIVE_JOBS_OBJTYPE_ID,    HOUR_COUNT_ATTR_ID);
    }};
    private static final Map<BigInteger, SelectorCreator> mapSelectorCreators = new HashMap<BigInteger, SelectorCreator>(){{
            put(S_INSTANCE_INFO_OBJTYPE_ID, new SelectorInstanceInfoCreator());
            put(S_SIZE_TABLESPACE_OBJTYPE_ID, new SelectorSizeForTablespaceCreator());
            put(S_SIZE_INDEX_LOB_OBJTYPE_ID, new SelectorSizeForIndexLobCreator());
            put(S_LAST_ERRORS_OBJTYPE_ID, new SelectorLastErrorsCreator());
            put(S_ACTIVE_SESSIONS_OBJTYPE_ID, new SelectorActiveSessionsCreator());
            put(S_ACTIVE_QUERIES_OBJTYPE_ID, new SelectorActiveQueriesCreator());
            put(S_QUERIES_RESULTS_OBJTYPE_ID, new SelectorQueriesResultsCreator());
            put(S_SQL_MONITOR_OBJTYPE_ID, new SelectorSqlQueryMonitorCreator());
            put(S_DB_LOCKS_OBJTYPE_ID, new SelectorDBLocksCreator());
            put(S_ACTIVE_JOBS_OBJTYPE_ID, new SelectorActiveJobsCreator());
        }};


    public Map<BigInteger, SelectorCreator> getSelectorCreators(){
        return mapSelectorCreators;
    }

    private enum HWProjectColumnName {
        id("id"),
        name("name"),
        createDate("creation_date"),
        author("author"),
        authorName("author_name"),
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

    private class CuttedHealthMonitorProjectRowMapper implements RowMapper<HealthMonitorProject> {
        public HealthMonitorProject mapRow(ResultSet rs, int rownum) throws SQLException {
            HealthMonitorProject.Builder builder =  mapCuttedProject(rs, rownum);
            return builder.buildProject();
        }
    }

    private class CompleteHealthMonitorProjectRowMapper implements RowMapper<HealthMonitorProject> {
        public HealthMonitorProject mapRow(ResultSet rs, int rownum) throws SQLException {
            HealthMonitorProject.Builder builder =  mapCuttedProject(rs, rownum);
            HealthMonitorProject project = builder.buildProject();
            builder.buildGraphic(getProjectGraph(project));
            Map<BigInteger, Selector> selectors = getProjectSelectors(project);
            builder.buildSelectors(selectors);
            return builder.buildProject();
        }
    }

    private  HealthMonitorProject.Builder mapCuttedProject(ResultSet rs, int rownum) throws SQLException {
        BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
        String name = rs.getString(HWProjectColumnName.name.toString());
        Date creationDate = rs.getDate(HWProjectColumnName.createDate.toString());
        String description = rs.getString(HWProjectColumnName.description.toString());
        BigInteger author = BigInteger.valueOf(rs.getLong(HWProjectColumnName.author.toString()));
        String authorName = rs.getString(HWProjectColumnName.authorName.toString());
        String sid = rs.getString(HWProjectColumnName.sid.toString());
        String port = rs.getString(HWProjectColumnName.port.toString());
        String serverName = rs.getString(HWProjectColumnName.serverName.toString());
        String userName = rs.getString(HWProjectColumnName.userName.toString());
        String password = rs.getString(HWProjectColumnName.password.toString());
        HealthMonitorProject.Builder builder = new HealthMonitorProject.Builder(id, name, creationDate,
                description, author, authorName, sid, port, serverName, userName, password);
        return builder;
    }

    private class GraphicHMRowMapper implements RowMapper<GraphicHMImpl> {
        public GraphicHMImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
            BigInteger id = BigInteger.valueOf(rs.getLong(HWProjectColumnName.id.toString()));
            Integer hourCount = rs.getInt(HWProjectColumnName.hourCount.toString());
            String name = rs.getString(HWProjectColumnName.name.toString());

            GraphicHMImpl.HMGraphBuilder builder = new GraphicHMImpl.HMGraphBuilder();
            builder.buildId(id);
            builder.buildHourCount(hourCount);
            builder.buildName(name);

            Clob graphicInClobType = rs.getClob(HWProjectColumnName.json.toString());
           // LOGGER.info("clob = " + graphicInClobType);
            String clobString = ClobToStringService.clobToString(graphicInClobType);
            //LOGGER.info("clobString = " + clobString);
            //JsonObject jsonObj = new JsonObject().getAsJsonObject(clobString);
            JsonObject graphicInJsonType = new JsonParser().parse(clobString).getAsJsonObject();
            //LOGGER.info("jsonObj = " + graphicInJsonType);
            builder.buildGraphicJson(graphicInJsonType);
            return builder.buildGraphic();
        }
    }

    private class SelectorInstanceInfoRowMapper implements RowMapper<Selector> {
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

    private class SelectorSizeForTablespaceRowMapper implements RowMapper<Selector> {
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

    private class SelectorSizeForIndexLobRowMapper implements RowMapper<Selector> {
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

    private class SelectorLastErrorsRowMapper implements RowMapper<Selector> {
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

    private class SelectorActiveSessionsRowMapper implements RowMapper<Selector> {
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

    private class SelectorActiveQueriesRowMapper implements RowMapper<Selector> {
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

    private class SelectorQueriesResultsRowMapper implements RowMapper<Selector> {
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

    private class SelectorSqlQueryMonitorRowMapper implements RowMapper<Selector> {
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

    private class SelectorDBLocksRowMapper implements RowMapper<Selector> {
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

    private class SelectorActiveJobsRowMapper implements RowMapper<Selector> {
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
