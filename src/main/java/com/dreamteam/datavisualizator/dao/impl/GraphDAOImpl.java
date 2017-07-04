package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.common.configurations.HMDataSource;
import com.dreamteam.datavisualizator.common.exceptions.ConnectionException;
import com.dreamteam.datavisualizator.dao.GraphDAO;
import com.dreamteam.datavisualizator.models.GraphType;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.GraphProject;
import com.dreamteam.datavisualizator.services.ClobToStringService;
import com.dreamteam.datavisualizator.services.graphService.ObjectGraph;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class GraphDAOImpl extends AbstractDAO implements GraphDAO {
    private static final Logger LOGGER = Logger.getLogger(GraphDAOImpl.class);
    private JdbcTemplate templateGraph;

    @Autowired
    private JdbcTemplate generalTemplate;

    @Override
    public String getProjectName(BigInteger id) {
        try {
            String projectName = generalTemplate.queryForObject(SELECT_PROJECT_NAME_BY_ID, new Object[]{id}, String.class);
            return projectName;
        } catch (DataAccessException e) {
            LOGGER.error("Project name not fetched by id " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Project name not fetched by id " + id, e);
            return null;
        }
    }

    @Override
    public void setDataSourceTemplate(String serverName, String port, String sid, String username, String password) {
        String url = "jdbc:oracle:thin:@" + serverName + ":" + port + "/" + sid;
        HMDataSource dataSourceHM = new HMDataSource(url, username, password);
        try {
            templateGraph = new JdbcTemplate(dataSourceHM.createDataSource());
            templateGraph.queryForList("SELECT 1 FROM DUAL");
        } catch (DataAccessException e) {
            LOGGER.error("Connection with parameters SERVER - " + serverName + "; PORT - " + port + "; SID - "
                    + sid + "; USER NAME - " + username + "; PASSWORD - " + password + " have error: \n", e);
            throw new ConnectionException("Connection with wrong parameters: SERVER - " + serverName + "; PORT - " + port + "; SID - "
                    + sid + "; USER NAME - " + username + "; PASSWORD - " + password + ".");
        } catch (Exception e) {
            LOGGER.error("Connection with parameters SERVER - " + serverName + "; PORT - " + port + "; SID - "
                    + sid + "; USER NAME - " + username + "; PASSWORD - " + password + " have error: \n", e);
            throw new ConnectionException("Connection with wrong parameters: SERVER - " + serverName + "; PORT - " + port + "; SID - "
                    + sid + "; USER NAME - " + username + "; PASSWORD - " + password + ".");
        }
    }

    @Override
    public Project getProjectById(BigInteger id) {
        try {
            return generalTemplate.queryForObject(SELECT_GRAPH_PROJECT_BY_ID, new Object[]{id}, new GrpahProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched by id " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Project not fetched by id " + id, e);
            return null;
        }
    }

    @Override
    public Project getProjectByName(String name) {
        try {
            return generalTemplate.queryForObject(SELECT_GRAPH_PROJECT_BY_NAME, new Object[]{name}, new GrpahProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched by id " + name, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Project not fetched by id " + name, e);
            return null;
        }
    }

    @Override
    public List<GraphProject> getProjectsByAuthor(User user) {
        try {
            if (user != null) {
                return generalTemplate.query(SELECT_GRAPH_PROJECTS_BY_AUTHOR, new Object[]{user.getId()}, new GrpahProjectRowMapper());
            } else {
                LOGGER.error("Projects for author wasn't selected");
                return null;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Projects not fetched by author (id:" + user.getId() + " name:" + user.getFullName() + ")", e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Project not fetched by author", e);
            return null;
        }
    }


    @Override
    public List<GraphProject> getProjectsUserHaveAccessTo(User user) {
        try {
            if (user != null) {
                return generalTemplate.query(SELECT_GRAPH_PROJECTS_USER_HAVE_ACCESS_TO, new Object[]{user.getId()}, new GrpahProjectRowMapper());
            } else {
                LOGGER.error("Projects for user wasn't selected because of user " + user);
                return null;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Accessible to user projects haven't been fetched  User(id:" + user.getId() + " name:" + user.getFullName() + ")", e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Accessible to user projects haven't been fetched  User(id:" + user.getId() + " name:" + user.getFullName() + ")", e);
            return null;
        }
    }

    @Override
    public boolean deleteProject(Project project) {
        try {
            if (project != null) {
                generalTemplate.update(DELETE_OBJECT, project.getId());         //deleting project
                return true;
            } else {
                LOGGER.error("Project was not removed because it's null");
                return false;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Project with id " + project.getId() + " not deleted", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Project not deleted", e);
            throw e;
        }
    }

    @Override
    public Project saveProject(Project project) {
        return null;
    }

    @Override
    public ObjectGraph getObjectGraphSimpleDB(BigInteger id) {
        try {
            return templateGraph.queryForObject(GET_OBJECT_BY_ID_SIMPLE, new Object[]{id}, new GraphObject());
        } catch (DataAccessException e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        }
    }


    @Override
    public List<ObjectGraph> getObjectsByReferSimpleDB(BigInteger id, BigInteger attribute) {
        try {
            return templateGraph.query(GET_LIST_CHILDREN_BY_REFERENCE_SIMPLE, new Object[]{id, attribute}, new GraphObject());
        } catch (DataAccessException e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        }
    }

    @Override
    public ObjectGraph getObjectGraphNCDB(BigInteger id) {
        try {
            return templateGraph.queryForObject(GET_OBJECT_BY_ID_NCDB, new Object[]{id}, new GraphObject());
        } catch (DataAccessException e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        }
    }


    @Override
    public List<ObjectGraph> getObjectsByReferNCDB(BigInteger id, BigInteger attribute) {
        try {
            return templateGraph.query(GET_LIST_CHILDREN_BY_REFERENCE_NCDB, new Object[]{id, attribute}, new GraphObject());
        } catch (DataAccessException e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        }
    }

    @Override
    public List<ObjectGraph> getObjectsByParentSimpleDB(BigInteger id) {
        try {
            return templateGraph.query(GET_LIST_CHILD_BY_PARENT_SIMPLE_DB, new Object[]{id}, new GraphObject());
        } catch (DataAccessException e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        }

    }

    @Override
    public List<ObjectGraph> getObjectsByParentNCDB(BigInteger id) {
        try {
            return templateGraph.query(GET_LIST_CHILD_BY_PARENT_NCDB, new Object[]{id}, new GraphObject());
        } catch (DataAccessException e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Object of graph not fetched by id = " + id, e);
            return null;
        }
    }


    private class GrpahProjectRowMapper implements RowMapper<GraphProject> {

        @Override
        public GraphProject mapRow(ResultSet rs, int rowNum) throws SQLException {
            BigInteger id = BigInteger.valueOf(rs.getLong(GraphRowName.id.toString()));
            String name = rs.getString(GraphRowName.name.toString());
            Date creationDate = rs.getDate(GraphRowName.createDate.toString());
            String description = rs.getString(GraphRowName.description.toString());
            BigInteger author = BigInteger.valueOf(rs.getLong(GraphRowName.author.toString()));
            String authorName = rs.getString(GraphRowName.authorName.toString());
            String sid = rs.getString(GraphRowName.sid.toString());
            String port = rs.getString(GraphRowName.port.toString());
            String serverName = rs.getString(GraphRowName.serverName.toString());
            String userName = rs.getString(GraphRowName.userName.toString());
            String password = rs.getString(GraphRowName.password.toString());
            BigInteger idFirstElementGraph = BigInteger.valueOf(rs.getLong(GraphRowName.idFirstElement.toString()));
            Clob graphClob = rs.getClob(GraphRowName.json.toString());
            String graphJson = ClobToStringService.clobToString(graphClob);
            JsonObject graphInJson = new JsonParser().parse(graphJson).getAsJsonObject();
            String graphType = rs.getString(GraphRowName.graphType.toString());
            GraphProject.GraphBuilder builder = new GraphProject.GraphBuilder(id, name, creationDate, description, author, authorName,
                    sid, port, serverName, userName, password, idFirstElementGraph, graphInJson);
            BigInteger referenceAttribute = BigInteger.valueOf(rs.getLong(GraphRowName.referenceAttribute.toString()));
            if (referenceAttribute != null) {
                builder.buildReferenceAttribute(referenceAttribute);
            }
            builder.buildGraphType(GraphType.valueOf(graphType));
            return builder.buildProject();
        }
    }

    private class GraphObject implements RowMapper<ObjectGraph> {
        @Override
        public ObjectGraph mapRow(ResultSet rs, int rowNum) throws SQLException {
            BigInteger id = BigInteger.valueOf(rs.getLong(GraphRowName.id.toString()));
            String name = rs.getString(GraphRowName.name.toString());
            BigInteger parentId = BigInteger.valueOf(rs.getLong(GraphRowName.parentId.toString()));
            BigInteger level = BigInteger.valueOf(rs.getLong(GraphRowName.level.toString()));
            return new ObjectGraph(id, name, parentId, level);
        }
    }

    private enum GraphRowName {
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
        idFirstElement("id_first_elem"),
        json("json"),
        referenceAttribute("reference_attribute"),
        parentId("parent_id"),
        graphType("graph_type"),
        level("level");

        private String columnName;

        GraphRowName(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private static final String GET_OBJECT_BY_ID_SIMPLE = "select object_id id, name from objects " +
            "where object_id = ?";

    private static final String GET_LIST_CHILDREN_BY_REFERENCE_SIMPLE = " select children.object_id id,children.name" +
            " from objects parent, objects children, objreference ref" +
            " where parent.object_id = ?" +
            " and ref.attr_id = ?" +
            " and ref.object_id = parent.object_id" +
            " and ref.reference = children.object_id";

    private static final String GET_OBJECT_BY_ID_NCDB = "select object_id id, name from nc_objects " +
            " where object_id = ?";
    private static final String GET_LIST_CHILDREN_BY_REFERENCE_NCDB = "select children.object_id id,children.name" +
            " from nc_objects parent, nc_objects children, nc_references ref" +
            " where parent.object_id = ?" +
            " and ref.attr_id = ?" +
            " and ref.object_id = parent.object_id" +
            " and ref.reference = children.object_id ";

    private static final String GET_LIST_CHILD_BY_PARENT_SIMPLE_DB = "select object_id id, parent_id, name, level" +
            " from objects" +
            " start with object_id = ?" +
            " connect by nocycle prior object_id = parent_id" +
            " order by level";

    private static final String GET_LIST_CHILD_BY_PARENT_NCDB = "select object_id id, parent_id, name, level" +
            " from nc_objects" +
            " start with object_id = ?" +
            " connect by nocycle prior object_id=parent_id" +
            " order by level";

    private static final String SELECT_GRAPH_PROJECT_BY_ID = "select grproject.object_id id, grproject.name name, creation_date.date_value creation_date,author.object_id author,author.name author_name,description.value description," +
            " sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password," +
            " id_first_elem.value id_first_elem, reference_attribute.value reference_attribute, graph_type.value graph_type, json.big_value json" +
            " from objects grproject, attributes creation_date,objects author, attributes description, attributes sid, attributes json," +
            " attributes port, attributes server_name, attributes user_name, attributes password, attributes id_first_elem, attributes reference_attribute, attributes graph_type, objreference ref " +
            " where grproject.object_type_id = 3" +
            " and grproject.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and grproject.object_id = description.object_id" +
            " and description.attr_id = 7" +
            " and grproject.object_id = sid.object_id" +
            " and sid.attr_id = 25" +
            " and grproject.object_id = port.object_id" +
            " and port.attr_id = 24" +
            " and grproject.object_id =  server_name.object_id" +
            " and server_name.attr_id = 23" +
            " and grproject.object_id = user_name.object_id" +
            " and user_name.attr_id = 26" +
            " and grproject.object_id = password.object_id" +
            " and password.attr_id = 4" +
            " and grproject.object_id = id_first_elem.object_id" +
            " and id_first_elem.attr_id = 27" +
            " and grproject.object_id = reference_attribute.object_id" +
            " and reference_attribute.attr_id = 28" +
            " and grproject.object_id = graph_type.object_id" +
            " and graph_type.attr_id = 29" +
            " and grproject.object_id = json.object_id" +
            " and json.attr_id = 13" +
            " and ref.attr_id = 17" +
            " and ref.object_id = grproject.object_id" +
            " and ref.reference = author.object_id" +
            " and grproject.object_id = ?";

    private static final String SELECT_GRAPH_PROJECT_BY_NAME = "select grproject.object_id id, grproject.name name, creation_date.date_value creation_date,author.object_id author,author.name author_name,description.value description," +
            " sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password," +
            " id_first_elem.value id_first_elem, reference_attribute.value reference_attribute, graph_type.value graph_type, json.big_value json" +
            " from objects grproject, attributes creation_date,objects author, attributes description, attributes sid, attributes json," +
            " attributes port, attributes server_name, attributes user_name, attributes password, attributes id_first_elem, attributes reference_attribute, attributes graph_type, objreference ref " +
            " where grproject.object_type_id = 3" +
            " and grproject.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and grproject.object_id = description.object_id" +
            " and description.attr_id = 7" +
            " and grproject.object_id = sid.object_id" +
            " and sid.attr_id = 25" +
            " and grproject.object_id = port.object_id" +
            " and port.attr_id = 24" +
            " and grproject.object_id =  server_name.object_id" +
            " and server_name.attr_id = 23" +
            " and grproject.object_id = user_name.object_id" +
            " and user_name.attr_id = 26" +
            " and grproject.object_id = password.object_id" +
            " and password.attr_id = 4" +
            " and grproject.object_id = id_first_elem.object_id" +
            " and id_first_elem.attr_id = 27" +
            " and grproject.object_id = reference_attribute.object_id" +
            " and reference_attribute.attr_id = 28" +
            " and grproject.object_id = graph_type.object_id" +
            " and graph_type.attr_id = 29" +
            " and grproject.object_id = json.object_id" +
            " and json.attr_id = 13" +
            " and ref.attr_id = 17" +
            " and ref.object_id = grproject.object_id" +
            " and ref.reference = author.object_id" +
            " and grproject.name  = ?";

    private static final String SELECT_PROJECT_NAME_BY_ID = "select objects.name name from objects where object_type_id = 4 and object_id = ?";

    private static final String SELECT_GRAPH_PROJECTS_BY_AUTHOR = "select grproject.object_id id, grproject.name name, creation_date.date_value creation_date,author.object_id author,author.name author_name,description.value description," +
            " sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password," +
            " id_first_elem.value id_first_elem, reference_attribute.value reference_attribute, graph_type.value graph_type, json.big_value json" +
            " from objects grproject, attributes creation_date,objects author, attributes description, attributes sid, attributes json," +
            " attributes port, attributes server_name, attributes user_name, attributes password, attributes id_first_elem, attributes reference_attribute, attributes graph_type, objreference ref " +
            " where grproject.object_type_id = 3" +
            " and grproject.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and grproject.object_id = description.object_id" +
            " and description.attr_id = 7" +
            " and grproject.object_id = sid.object_id" +
            " and sid.attr_id = 25" +
            " and grproject.object_id = port.object_id" +
            " and port.attr_id = 24" +
            " and grproject.object_id =  server_name.object_id" +
            " and server_name.attr_id = 23" +
            " and grproject.object_id = user_name.object_id" +
            " and user_name.attr_id = 26" +
            " and grproject.object_id = password.object_id" +
            " and password.attr_id = 4" +
            " and grproject.object_id = id_first_elem.object_id" +
            " and id_first_elem.attr_id = 27" +
            " and grproject.object_id = reference_attribute.object_id" +
            " and reference_attribute.attr_id = 28" +
            " and grproject.object_id = graph_type.object_id" +
            " and graph_type.attr_id = 29" +
            " and grproject.object_id = json.object_id" +
            " and json.attr_id = 13" +
            " and ref.attr_id = 17" +
            " and ref.object_id = grproject.object_id" +
            " and ref.reference = author.object_id" +
            " and author.object_id = ?" +
            " order by creation_date.date_value";

    private static final String SELECT_GRAPH_PROJECTS_USER_HAVE_ACCESS_TO = "select grproject.object_id id, grproject.name name, creation_date.date_value creation_date,author.object_id author,author.name author_name,description.value description," +
            " sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password," +
            " id_first_elem.value id_first_elem, reference_attribute.value reference_attribute, graph_type.value graph_type, json.big_value json" +
            " from objects grproject, attributes creation_date,objects author, attributes description, attributes sid, attributes json, objects have_access, objreference ref_access," +
            " attributes port, attributes server_name, attributes user_name, attributes password, attributes id_first_elem, attributes reference_attribute, attributes graph_type, objreference ref " +
            " where grproject.object_type_id = 3" +
            " and grproject.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and grproject.object_id = description.object_id" +
            " and description.attr_id = 7" +
            " and grproject.object_id = sid.object_id" +
            " and sid.attr_id = 25" +
            " and grproject.object_id = port.object_id" +
            " and port.attr_id = 24" +
            " and grproject.object_id =  server_name.object_id" +
            " and server_name.attr_id = 23" +
            " and grproject.object_id = user_name.object_id" +
            " and user_name.attr_id = 26" +
            " and grproject.object_id = password.object_id" +
            " and password.attr_id = 4" +
            " and grproject.object_id = id_first_elem.object_id" +
            " and id_first_elem.attr_id = 27" +
            " and grproject.object_id = reference_attribute.object_id" +
            " and reference_attribute.attr_id = 28" +
            " and grproject.object_id = graph_type.object_id" +
            " and graph_type.attr_id = 29" +
            " and grproject.object_id = json.object_id" +
            " and json.attr_id = 13" +
            " and ref.attr_id = 17" +
            " and ref.object_id = grproject.object_id" +
            " and ref.reference = author.object_id" +
            " and ref_access.object_id = grproject.object_id" +
            " and ref_access.reference = have_access.object_id" +
            " and have_access.object_id = ?" +
            " order by creation_date.date_value";
}
