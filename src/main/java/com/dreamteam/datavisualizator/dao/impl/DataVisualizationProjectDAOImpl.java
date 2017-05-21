package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.GraphicDVImpl;
import com.google.gson.JsonObject;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.dreamteam.datavisualizator.common.IdList.*;

@Repository
public class DataVisualizationProjectDAOImpl extends AbstractDAO implements DataVisualizationProjectDAO{
    private static final Logger LOGGER = Logger.getLogger(DataVisualizationProjectDAOImpl.class);


    @Autowired
    private SimpleJdbcCall simpleCallTemplate;

    @Autowired
    private JdbcTemplate generalTemplate;

    public Project getProjectById(BigInteger id) {
        try {
            return generalTemplate.queryForObject(SELECT_DVPROJECT_BY_ID, new Object[]{id}, new DataVisualizationProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched", e);
            return null;
        }
    }

    public Project getProjectByName(String projectName) {
        try {
            return generalTemplate.queryForObject(SELECT_DVPROJECT_BY_NAME, new Object[]{projectName}, new DataVisualizationProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Project not fetched", e);
            return null;
        }
    }

    public List<DataVisualizationProject> getProjectsByAuthor(User user) {
        try {
            return generalTemplate.query(SELECT_DV_PROJECTS_BY_AUTHOR, new Object[]{user.getId()}, new DataVisualizationProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Projects not fetched", e);
            return null;
        }
    }

    public List<DataVisualizationProject> getProjectsUserHaveAccessTo(User user) {
        try {
            return generalTemplate.query(SELECT_DV_PROJECTS_USER_HAVE_ACCESS_TO, new Object[]{user.getId()}, new DataVisualizationProjectRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Projects not fetched", e);
            return null;
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean deleteProject(Project project) {
        try {
            //TODO method for deleting a project
            return false;
        } catch (DataAccessException e) {
            LOGGER.error("Project not removed", e);
            return false;
        } catch (Exception e){
            LOGGER.error("Project not removed", e);
            return false;
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public Project saveProject(String name, BigInteger authorId, String description, List<Graphic> graphics) {
        Date projectCreationDate = new Date();
        Project project = new DataVisualizationProject.Builder(name, projectCreationDate, authorId).description(description).graphics(graphics).build();
        try {
            BigInteger insertedObjectId = createObject(name, DATA_VISUALIZATION_PROJECT_OBJTYPE_ID);
            generalTemplate.update(INSERT_ATTR_VALUE, PROJECT_DESCRIPTION_ATTR_ID, insertedObjectId, description);
            generalTemplate.update(INSERT_ATTR_DATE_VALUE, PROJECT_DATE_ATTR_ID, insertedObjectId, projectCreationDate);
            generalTemplate.update(INSERT_OBJREFERENCE_RELATION, PROJECT_AUTHOR_RELATION_ATTR_ID, authorId, insertedObjectId);
            //TODO Graphs save mechanics
        } catch (DataAccessException e) {
            LOGGER.error("Project not saved", e);
            return null;
        } catch (Exception e){
            LOGGER.error("Project not saved", e);
            return null;
        }
        return project;
    }

    public List<Graphic> getProjectGraphs(Project project) {
        try {
            //TODO method for fetching list of projects from db
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Graphs not fetched", e);
            return null;
        }
    }

    public void getPreviewProjectDataForUser(User user) {
        //TODO method for fetching data about projects from db for preview
    }

    private static final String SELECT_DVPROJECT_BY_ID = "select dvpoject.object_id id, dvpoject.name name, creation_date.date_value creation_date, author.object_id author, description.value description" +
            " from objects dvpoject, attributes creation_date, objects author, attributes description, objreference ref" +
            " where dvpoject.object_type_id = 4" +
            " and dvpoject.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and dvpoject.object_id = description.object_id" +
            " and description.attr_id = 7" +
            " and ref.attr_id = 17" +
            " and ref.object_id = dvpoject.object_id" +
            " and ref.reference = author.object_id" +
            " and dvpoject.object_id = ?";
    private static final String SELECT_DVPROJECT_BY_NAME = "select dvpoject.object_id id, dvpoject.name name, creation_date.date_value creation_date, author.object_id author, description.value description" +
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
    private static String SELECT_DV_PROJECTS_BY_AUTHOR = "select dvpoject.object_id id, dvpoject.name name, creation_date.date_value creation_date, author.object_id author, description.value description" +
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
    private static final String SELECT_DV_PROJECTS_USER_HAVE_ACCESS_TO = "select dvpoject.object_id id, dvpoject.name name, creation_date.date_value creation_date, author.object_id author, description.value description" +
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

    private enum DVProjectColumnName {
        id("id"),
        name("name"),
        createDate("create_date"),
        author("author"),
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
            DataVisualizationProject.Builder builder = new DataVisualizationProject.Builder(
                    rs.getString(DVProjectColumnName.name.toString()),
                    rs.getDate(DVProjectColumnName.createDate.toString()),
                    BigInteger.valueOf(rs.getLong(DVProjectColumnName.author.toString()))
            );
            builder.id(BigInteger.valueOf(rs.getLong(DVProjectColumnName.id.toString())));
            builder.description(rs.getString(DVProjectColumnName.description.toString()));
//            builder.usersProjectAccessible();
//            builder.graphics();
            return builder.build();
        }
    }

    private class GraphicDVRowMapper implements RowMapper<GraphicDVImpl> {
        public GraphicDVImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
            GraphicDVImpl.DVGraphBuilder builder = new GraphicDVImpl.DVGraphBuilder();
            builder.buildId(BigInteger.valueOf(rs.getLong(DVProjectColumnName.id.toString())));
            builder.buildName(rs.getString(DVProjectColumnName.name.toString()));
            builder.buildGraphicJSON(new JsonObject().getAsJsonObject(rs.getClob(DVProjectColumnName.json.toString()).toString()));
            builder.buildAverage(BigDecimal.valueOf(rs.getLong(DVProjectColumnName.average.toString())));
            builder.buildOlympicAverage(BigDecimal.valueOf(rs.getLong(DVProjectColumnName.olympicAverage.toString())));
            builder.buildDispersion(BigDecimal.valueOf(rs.getLong(DVProjectColumnName.dispersion.toString())));
            builder.buildMathExpectation(BigDecimal.valueOf(rs.getLong(DVProjectColumnName.mathExpectation.toString())));
            return builder.build();
        }
    }


}
