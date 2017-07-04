package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.common.configurations.HMDataSource;
import com.dreamteam.datavisualizator.common.exceptions.ConnectionException;
import com.dreamteam.datavisualizator.dao.GraphDAO;
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
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class GraphDAOImpl implements GraphDAO {
    private static final Logger LOGGER = Logger.getLogger(GraphDAOImpl.class);
    private JdbcTemplate templateGraph;

    @Autowired
    private JdbcTemplate generalTemplate;

    @Autowired
    private SimpleJdbcCall simpleCallTemplate;


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
        return null;
    }

    @Override
    public List<GraphProject> getProjectsByAuthor(User user) {
        return null;
    }

    @Override
    public List<GraphProject> getProjectsUserHaveAccessTo(User user) {
        return null;
    }

    @Override
    public boolean deleteProject(Project project) {
        return false;
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
            GraphProject.GraphBuilder builder = new GraphProject.GraphBuilder(id, name, creationDate, description, author, authorName,
                    sid, port, serverName, userName, password, idFirstElementGraph, graphInJson);
            BigInteger referenceAttribute = BigInteger.valueOf(rs.getLong(GraphRowName.referenceAttribute.toString()));
            if (referenceAttribute != null) {
                builder.buildReferenceAttribute(referenceAttribute);
            }
            return builder.buildProject();
        }
    }

    private class GraphObject implements RowMapper<ObjectGraph> {
        @Override
        public ObjectGraph mapRow(ResultSet rs, int rowNum) throws SQLException {
            BigInteger id = BigInteger.valueOf(rs.getLong(GraphRowName.id.toString()));
            String name = rs.getString(GraphRowName.name.toString());
            BigInteger parentId = BigInteger.valueOf(rs.getLong(GraphRowName.parentId.toString()));
            return new ObjectGraph(id, name, parentId);
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
        graphType("graph_type");

        private String columnName;

        GraphRowName(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private String GET_OBJECT_BY_ID_SIMPLE = "select object_id id, name from objects " +
            "where object_id = ?";

    private String GET_LIST_CHILDREN_BY_REFERENCE_SIMPLE = " select children.object_id id,children.name" +
            " from objects parent, objects children, objreference ref" +
            " where parent.object_id = ?" +
            " and ref.attr_id = ?" +
            " and ref.object_id = parent.object_id" +
            " and ref.reference = children.object_id";

    private String GET_OBJECT_BY_ID_NCDB = "select object_id id, name from nc_objects " +
            " where object_id = ?";
    private String GET_LIST_CHILDREN_BY_REFERENCE_NCDB = "select children.object_id id,children.name" +
            " from nc_objects parent, nc_objects children, nc_references ref" +
            " where parent.object_id = ?" +
            " and ref.attr_id = ?" +
            " and ref.object_id = parent.object_id" +
            " and ref.reference = children.object_id ";

    private String GET_LIST_CHILD_BY_PARENT_SIMPLE_DB = "select object_id id, parent_id, name" +
            " from objects" +
            " start with object_id = ?" +
            " connect by nocycle prior object_id = parent_id" +
            " order by level";

    private String GET_LIST_CHILD_BY_PARENT_NCDB = "select object_id id, parent_id, name" +
            " from nc_objects" +
            " start with object_id = ?" +
            " connect by nocycle prior object_id=parent_id" +
            " order by level";
}
