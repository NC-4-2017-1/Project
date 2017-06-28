package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.common.IdList;
import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Correlation;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.GraphicDVImpl;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.dreamteam.datavisualizator.common.IdList.*;
import static java.math.BigDecimal.ROUND_CEILING;

@Repository
public class DataVisualizationProjectDAOImpl extends AbstractDAO implements DataVisualizationProjectDAO {
    private static final Logger LOGGER = Logger.getLogger(DataVisualizationProjectDAOImpl.class);

    @Autowired
    private SimpleJdbcCall simpleCallTemplate;

    @Autowired
    private JdbcTemplate generalTemplate;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Project getProjectById(BigInteger id) {
        try {
            Project project = generalTemplate.queryForObject(SELECT_DVPROJECT_BY_ID, new Object[]{id}, new DataVisualizationProjectRowMapper());
            List<Graphic> graphics = getProjectGraphs(project);
            List<User> usersHaveAccessToProject = userDAO.getUsersThatHaveAccessToProject(id, null);
            ((DataVisualizationProject) project).setGraphics(graphics);
            ((DataVisualizationProject) project).setUsersProjectAccessible(usersHaveAccessToProject);

            return project;
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched by id " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Project not fetched by id " + id, e);
            return null;
        }
    }

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
    public Project getProjectByName(String projectName) {
        try {
            return generalTemplate.queryForObject(SELECT_DVPROJECT_BY_NAME, new Object[]{projectName}, new DataVisualizationProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched by name " + projectName, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Project not fetched by name " + projectName, e);
            return null;
        }
    }

    @Override
    public List<DataVisualizationProject> getProjectsByAuthor(User user) {
        try {
            if (user != null) {
                return generalTemplate.query(SELECT_DV_PROJECTS_BY_AUTHOR, new Object[]{user.getId()}, new DataVisualizationProjectRowMapper());
            } else {
                LOGGER.error("Projects for author wasn't selected because of author " + user);
                return null;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Projects not fetched by author (id:" + user.getId() + " name:" + user.getFullName() + ")", e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Projects not fetched by author (id:" + user.getId() + " name:" + user.getFullName() + ")", e);
            return null;
        }
    }

    @Override
    public List<DataVisualizationProject> getProjectsUserHaveAccessTo(User user) {
        try {
            if (user != null) {
                return generalTemplate.query(SELECT_DV_PROJECTS_USER_HAVE_ACCESS_TO, new Object[]{user.getId()}, new DataVisualizationProjectRowMapper());
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
    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean deleteProject(Project project) {
        try {
            if (project != null) {
                List<Graphic> graphs = getProjectGraphs(project); // selecting all graphs
                for (Graphic graph : graphs) {
                    GraphicDVImpl graphicDV = (GraphicDVImpl) graph;
                    if (graphicDV.getCorrelation() != null) {
                        for (Correlation correlation : graphicDV.getCorrelation().keySet()) {
                            generalTemplate.update(DELETE_OBJECT, correlation.getId());
                        }
                    }
                    generalTemplate.update(DELETE_OBJECT, graph.getId());  // deleting all graphs
                }
                generalTemplate.update(DELETE_OBJECT, project.getId()); //deleting project object cascade
                return true;
            } else {
                LOGGER.error("Project was not removed because it's null");
                return false;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Project was not removed id:" + project.getId() + ", name:" + project.getName(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Project was not removed id:" + project.getId() + ", name:" + project.getName(), e);
            return false;
        }
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public Project saveProject(Project project) {
        Date projectCreationDate = new Date();
        try {
            if (project != null) {
                BigInteger insertedObjectId = createObject(project.getName(), DATA_VISUALIZATION_PROJECT_OBJTYPE_ID);
                generalTemplate.update(INSERT_ATTR_VALUE, PROJECT_DESCRIPTION_ATTR_ID, insertedObjectId, project.getDescription());
                generalTemplate.update(INSERT_ATTR_DATE_VALUE, PROJECT_DATE_ATTR_ID, insertedObjectId, projectCreationDate);
                generalTemplate.update(INSERT_OBJREFERENCE_RELATION, PROJECT_AUTHOR_RELATION_ATTR_ID, project.getAuthor(), insertedObjectId);
                DataVisualizationProject projectDV = (DataVisualizationProject) project;
                for (Graphic graphic : projectDV.getGraphics()) {
                    GraphicDVImpl graphicDV = (GraphicDVImpl) graphic;
                    saveGraphic(graphicDV, insertedObjectId);
                }
                project = getProjectById(insertedObjectId);
                return project;
            } else {
                LOGGER.error("Project was not save because it's null");
                return null;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Project not saved with input params name:" + project.getName() + ", author_id:" +
                    project.getAuthor() + ", description:" + project.getDescription() + ", graphics:" + ((DataVisualizationProject) project).getGraphics(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Project not saved with input params name:" + project.getName() + ", author_id:" +
                    project.getAuthor() + ", description:" + project.getDescription() + ", graphics:" + ((DataVisualizationProject) project).getGraphics(), e);
            return null;
        }
    }

    private GraphicDVImpl saveGraphic(GraphicDVImpl graphic, BigInteger projectId) {
        try {
            String graphicName = graphic.getName();
            JsonObject graphicJSON = graphic.getGraphicJSON();
            BigDecimal avg = graphic.getAverage();
            BigDecimal olympicAvg = graphic.getOlympicAverage();
            BigDecimal dispersion = graphic.getDispersion();
            BigDecimal mathExpectation = graphic.getMathExpectation();

            BigInteger graphicId = createObject(graphicName, IdList.GRAPHIC_OBJTYPE_ID);

            generalTemplate.update(INSERT_ATTR_VALUE, IdList.AVERAGE_DVPROJECT_ATTR_ID, graphicId, avg.setScale(3, ROUND_CEILING));
            generalTemplate.update(INSERT_ATTR_VALUE, IdList.OLYMPIC_AVERAGE_DVPROJECT_ATTR_ID, graphicId, olympicAvg.setScale(3, ROUND_CEILING));
            generalTemplate.update(INSERT_ATTR_VALUE, IdList.DISPERSION_DVPROJECT_ATTR_ID, graphicId, dispersion.setScale(3, ROUND_CEILING));
            generalTemplate.update(INSERT_ATTR_VALUE, IdList.MATH_EXPECTATION_DVPROJECT_ATTR_ID, graphicId, mathExpectation.setScale(3, ROUND_CEILING));
            generalTemplate.update(INSERT_OBJREFERENCE_RELATION, IdList.PROJECT_GRAPHICS_RELATION_ATTR_ID, graphicId, projectId);
            generalTemplate.update(INSERT_ATTR_BIG_VALUE, IdList.JSON_RESULT_ATTR_ID, graphicId, graphicJSON.toString());
            if (graphic.getCorrelation() != null) {
                for (Correlation correlation : graphic.getCorrelation().keySet()) {
                    BigInteger correlationId = createObject(correlation.getName(), CORRELATION_OBJTYPE_ID);
                    generalTemplate.update(INSERT_ATTR_VALUE, CALCULATE_VALUE_ATTR_ID, correlationId, correlation.getCorrelation());
                    generalTemplate.update(INSERT_OBJREFERENCE_RELATION, CORR_FIRST_GRAPHICS_RELATION_ATTR_ID, graphic.getId(), correlationId);
                    generalTemplate.update(INSERT_OBJREFERENCE_RELATION, CORR_SECOND_GRAPHICS_RELATION_ATTR_ID, graphic.getCorrelation().get(correlation).getId(), correlationId);
                }
            }
            return getGraphById(graphicId);
        } catch (DataAccessException e) {
            LOGGER.error("Error in inserting graphic + " + graphic.getName() + " to project with id:" + projectId, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error in inserting graphic + " + graphic.getName() + " to project with id:" + projectId, e);
            return null;
        }

    }

    private GraphicDVImpl getGraphById(BigInteger graphicId) {
        try {
            return (GraphicDVImpl) generalTemplate.queryForObject(SELECT_GRAPH_BY_ID, new Object[]{graphicId}, new GraphicDVRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Graph not fetched by id:" + graphicId, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Graph not fetched by id:" + graphicId, e);
            return null;
        }
    }


    @Override
    public List<Graphic> getProjectGraphs(Project project) {
        try {
            if (project != null) {
                return generalTemplate.query(SELECT_PROJECT_GRAPHS, new Object[]{project.getId()}, new GraphicDVRowMapper());
            } else {
                return null;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Graphs not fetched for project id:" + project.getId() + " name:" + project.getName(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Graphs not fetched for project id:" + project.getId() + " name:" + project.getName(), e);
            return null;
        }
    }


    private static final String SELECT_DVPROJECT_BY_ID = "select dvpoject.object_id id, dvpoject.name name, " +
            "creation_date.date_value creation_date, author.object_id author, author.name author_name, description.value description " +
            " from objects dvpoject, attributes creation_date, objects author, attributes description, objreference ref " +
            " where dvpoject.object_type_id = 4 " +
            " and dvpoject.object_id = creation_date.object_id " +
            " and creation_date.attr_id = 6 " +
            " and dvpoject.object_id = description.object_id " +
            " and description.attr_id = 7 " +
            " and ref.attr_id = 17 " +
            " and ref.object_id = dvpoject.object_id " +
            " and ref.reference = author.object_id " +
            " and dvpoject.object_id = ?";
    private static final String SELECT_DVPROJECT_BY_NAME = "select dvpoject.object_id id, dvpoject.name name," +
            " creation_date.date_value creation_date, author.object_id author, author.name author_name,description.value description" +
            " from objects dvpoject, attributes creation_date, objects author, attributes description, objreference ref" +
            " where dvpoject.object_type_id = 4" +
            " and dvpoject.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and dvpoject.object_id = description.object_id" +
            " and description.attr_id = 7" +
            " and ref.attr_id = 17" +
            " and ref.object_id = dvpoject.object_id" +
            " and ref.reference = author.object_id" +
            " and dvpoject.name = ?";
    private static final String SELECT_DV_PROJECTS_BY_AUTHOR = "select dvpoject.object_id id, dvpoject.name name," +
            " creation_date.date_value creation_date, author.object_id author, author.name author_name,description.value description" +
            " from objects dvpoject, attributes creation_date, objects author, attributes description, objreference ref" +
            " where dvpoject.object_type_id = 4" +
            " and dvpoject.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and dvpoject.object_id = description.object_id" +
            " and description.attr_id = 7" +
            " and ref.attr_id = 17" +
            " and ref.object_id = dvpoject.object_id" +
            " and ref.reference = author.object_id" +
            " and author.object_id=?" +
            " order by creation_date.date_value";
    private static final String SELECT_DV_PROJECTS_USER_HAVE_ACCESS_TO = "select dvpoject.object_id id, dvpoject.name name," +
            " creation_date.date_value creation_date, author.object_id author,author.name author_name, description.value description" +
            " from objects dvpoject, attributes creation_date, objects have_access, attributes description, objects author, objreference ref_author, objreference ref_access" +
            " where dvpoject.object_type_id = 4" +
            " and dvpoject.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and dvpoject.object_id = description.object_id" +
            " and description.attr_id = 7" +
            " and ref_author.attr_id = 17" +
            " and ref_author.object_id = dvpoject.object_id" +
            " and ref_author.reference = author.object_id" +
            " and ref_access.attr_id = 18" +
            " and ref_access.object_id = dvpoject.object_id" +
            " and ref_access.reference = have_access.object_id" +
            " and have_access.object_id = ?" +
            " order by creation_date.date_value";
    private static final String SELECT_PROJECT_GRAPHS = "select graph.object_id id, graph.name name, json_res.big_value json," +
            " average.value average, olympic_average.value olympic_average, math_expectation.value math_expectation, dispersion.value dispersion" +
            " from objects graph, objects dvproject, attributes json_res, attributes average, attributes olympic_average," +
            " attributes math_expectation, attributes dispersion, objreference ref" +
            " where graph.object_id = json_res.object_id" +
            " and json_res.attr_id = 13" +
            " and graph.object_id = average.object_id" +
            " and average.attr_id = 9" +
            " and graph.object_id = olympic_average.object_id" +
            " and olympic_average.attr_id = 10" +
            " and graph.object_id = math_expectation.object_id" +
            " and math_expectation.attr_id = 11" +
            " and graph.object_id = dispersion.object_id" +
            " and dispersion.attr_id = 12" +
            " and ref.attr_id = 20" +
            " and ref.reference = graph.object_id" +
            " and ref.object_id = dvproject.object_id" +
            " and dvproject.object_id = ?";

    private static final String SELECT_GRAPH_BY_ID = "select graph.object_id id, graph.name name, json_res.big_value json, " +
            " average.value average, olympic_average.value olympic_average, math_expectation.value math_expectation, dispersion.value dispersion " +
            " from objects graph, attributes json_res, attributes average, attributes olympic_average, attributes math_expectation, attributes dispersion " +
            " where graph.object_id = json_res.object_id " +
            " and json_res.attr_id = 13 " +
            " and graph.object_id = average.object_id " +
            " and average.attr_id = 9  " +
            " and graph.object_id = olympic_average.object_id " +
            " and olympic_average.attr_id = 10 " +
            " and graph.object_id = math_expectation.object_id " +
            " and math_expectation.attr_id = 11 " +
            " and graph.object_id = dispersion.object_id " +
            " and dispersion.attr_id = 12 " +
            " and graph.object_id = ?";

    private static final String SELECT_PROJECT_NAME_BY_ID = "select objects.name name from objects where object_type_id = 4 and object_id = ?";

    private enum DVProjectColumnName {
        id("id"),
        name("name"),
        createDate("creation_date"),
        author("author"),
        authorName("author_name"),
        description("description"),
        json("json"),
        average("average"),
        olympicAverage("olympic_average"),
        dispersion("dispersion"),
        mathExpectation("math_expectation");
        private final String columnName;

        private DVProjectColumnName(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private class DataVisualizationProjectRowMapper implements RowMapper<DataVisualizationProject> {
        public DataVisualizationProject mapRow(ResultSet rs, int rownum) throws SQLException {
            String name = rs.getString(DVProjectColumnName.name.toString());
            Date creationDate = rs.getDate(DVProjectColumnName.createDate.toString());
            BigInteger author = BigInteger.valueOf(rs.getLong(DVProjectColumnName.author.toString()));
            String authorName = rs.getString(DVProjectColumnName.authorName.toString());
            BigInteger id = BigInteger.valueOf(rs.getLong(DVProjectColumnName.id.toString()));
            String description = rs.getString(DVProjectColumnName.description.toString());

            DataVisualizationProject.Builder builder = new DataVisualizationProject.Builder(name, creationDate, author, authorName);
            builder.buildId(id);
            builder.buildDescription(description);
            return builder.buildProject();
        }
    }

    private class GraphicDVRowMapper implements RowMapper<Graphic> {
        public GraphicDVImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
            BigInteger id = rs.getBigDecimal(DVProjectColumnName.id.toString()).toBigInteger();
            String name = rs.getString(DVProjectColumnName.name.toString());

            Clob graphicClob = rs.getClob(DVProjectColumnName.json.toString());
            String clobString = ClobToStringService.clobToString(graphicClob);
            JsonObject graphicInJsonType = new JsonParser().parse(clobString).getAsJsonObject();

            String averageStr = rs.getString(DVProjectColumnName.average.toString());
            BigDecimal average = new BigDecimal(averageStr.replaceAll(",", "."));

            String olympicAverageStr = rs.getString(DVProjectColumnName.olympicAverage.toString());
            BigDecimal olympicAverage = new BigDecimal(olympicAverageStr.replaceAll(",", "."));

            String dispersionStr = rs.getString(DVProjectColumnName.dispersion.toString());
            BigDecimal dispersion = new BigDecimal(dispersionStr.replaceAll(",", "."));

            String mathExpectationStr = rs.getString(DVProjectColumnName.mathExpectation.toString());
            BigDecimal mathExpectation = new BigDecimal(mathExpectationStr.replaceAll(",", "."));

            //TODO build correlation

            GraphicDVImpl.DVGraphBuilder builder = new GraphicDVImpl.DVGraphBuilder();
            builder.buildId(id);
            builder.buildName(name);
            builder.buildGraphicJSON(graphicInJsonType);
            builder.buildAverage(average);
            builder.buildOlympicAverage(olympicAverage);
            builder.buildDispersion(dispersion);
            builder.buildMathExpectation(mathExpectation);
            return builder.buildGraphic();
        }
    }


}
