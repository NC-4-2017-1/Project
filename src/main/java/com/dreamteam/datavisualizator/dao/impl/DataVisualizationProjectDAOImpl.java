package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.GraphicDVImpl;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
public class DataVisualizationProjectDAOImpl implements DataVisualizationProjectDAO {
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


    @Autowired
    private SimpleJdbcCall simpleCallTemplate;

    @Autowired
    private JdbcTemplate generalTemplate;

    public Project getProjectById(BigInteger id) {
        return generalTemplate.queryForObject(SELECT_DVPROJECT_BY_ID, new Object[]{id}, new DataVisualizationProjectRowMapper());
    }

    public Project getProjectByName(String projectName) {
        return generalTemplate.queryForObject(SELECT_DVPROJECT_BY_NAME, new Object[]{projectName}, new DataVisualizationProjectRowMapper());
    }

    public List<DataVisualizationProject> getProjectsByAuthor(User user) {
        return generalTemplate.query(SELECT_DV_PROJECT_BY_AUTHOR, new Object[]{user.getId()}, new DataVisualizationProjectRowMapper());
    }

    public List<DataVisualizationProject> getProjectsUserHaveAccessTo(User user) {
        return generalTemplate.query(SELECT_DV_PROJECTS_USER_HAVE_ACCESS_TO, new Object[]{user.getId()}, new DataVisualizationProjectRowMapper());
    }

    public boolean deleteProject(Project project) {
        return false;
    }

    @Transactional
    public Project saveProject(String name, BigInteger authorId, String description, List<Graphic> graphics) {
        Date projectCreationDate = new Date();
        Project project = new DataVisualizationProject.Builder(name, projectCreationDate, authorId).description(description).graphics(graphics).build();
        BigInteger insertedObjectId = createProjectObject(name);
        generalTemplate.update(INSERT_DV_PROJECT_ATTR_VALUE, PROJECT_DESCRIPTION_ATTR_ID, insertedObjectId, description);
        generalTemplate.update(INSERT_DV_PROJECT_DATE_VALUE, PROJECT_DATE_ATTR_ID, insertedObjectId, projectCreationDate);
        generalTemplate.update(INSERT_DV_PROJECT_AUTHOR_RELATION, PROJECT_AUTHOR_RELATION_ATTR_ID, authorId, insertedObjectId);
        //!TODO Graphics save mechanics
        return project;
    }

    private BigInteger createProjectObject(String name) {
        simpleCallTemplate.withFunctionName(INSERT_OBJECT);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("obj_type_id", DATA_VISUALIZATION_PROJECT_OBJTYPE_ID)
                .addValue("obj_name", name);
        return simpleCallTemplate.executeFunction(BigDecimal.class, in).toBigInteger();
    }

    public List<Graphic> getProjectGraphics(Project project) {
        return null;
    }

    public void getPreviewProjectDataForUser(User user) {

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

    private class GraphicDVRowMapper implements RowMapper<GraphicDVImpl>{
        public GraphicDVImpl mapRow(ResultSet rs, int rowNum) throws SQLException{
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

    private static final String INSERT_OBJECT = "insert_object";
    private static final String INSERT_DV_PROJECT_ATTR_VALUE = "insert into attributes(attr_id, object_id, value) values (?, ?, ?)";
    private static final String INSERT_DV_PROJECT_DATE_VALUE = "insert into attributes(attr_id, object_id, date_value) values (?, ?, ?)";
    private static final String INSERT_DV_PROJECT_AUTHOR_RELATION = "insert into objreference(attr_id, reference, object_id) values (?, ?, ?)";
    private static final String SELECT_DVPROJECT_BY_ID = "select objects.object_id id, objects.name name, creation_date.date_value creation_date,author.object_id author, description.value description" +
            " from OBJECTS, ATTRIBUTES creation_date,Objects author, ATTRIBUTES description, OBJREFERENCE ref" +
            " WHERE OBJECTS.OBJECT_ID=creation_date.object_id" +
            " AND creation_date.ATTR_id=" + PROJECT_DATE_ATTR_ID +
            " and OBJECTS.OBJECT_ID=description.object_id" +
            " and description.ATTR_ID=" + PROJECT_DESCRIPTION_ATTR_ID +
            " and ref.ATTR_ID=" + PROJECT_AUTHOR_RELATION_ATTR_ID +
            " and ref.OBJECT_ID=OBJECTS.OBJECT_ID" +
            " and ref.REFERENCE=author.OBJECT_ID" +
            " and objects.object_id=?";
    private static final String SELECT_DVPROJECT_BY_NAME = "select objects.object_id id, objects.name name, creation_date.date_value creation_date,author.object_id author, description.value description" +
            " from OBJECTS, ATTRIBUTES creation_date,Objects author, ATTRIBUTES description, OBJREFERENCE ref" +
            " WHERE OBJECTS.OBJECT_ID=creation_date.object_id" +
            " AND creation_date.ATTR_id=" + PROJECT_DATE_ATTR_ID +
            " and OBJECTS.OBJECT_ID=description.object_id" +
            " and description.ATTR_ID=" + PROJECT_DESCRIPTION_ATTR_ID +
            " and ref.ATTR_ID=" + PROJECT_AUTHOR_RELATION_ATTR_ID +
            " and ref.OBJECT_ID=OBJECTS.OBJECT_ID" +
            " and ref.REFERENCE=author.OBJECT_ID" +
            " and objects.name=?";
    private static String SELECT_DV_PROJECT_BY_AUTHOR = "select objects.object_id id, objects.name name, creation_date.date_value creation_date,author.object_id author, description.value description" +
            " from OBJECTS, ATTRIBUTES creation_date,Objects author, ATTRIBUTES description, OBJREFERENCE ref" +
            " WHERE OBJECTS.OBJECT_ID=creation_date.object_id" +
            " AND creation_date.ATTR_id=" + PROJECT_DATE_ATTR_ID +
            " and OBJECTS.OBJECT_ID=description.object_id" +
            " and description.ATTR_ID=" + PROJECT_DESCRIPTION_ATTR_ID +
            " and ref.ATTR_ID=" + PROJECT_AUTHOR_RELATION_ATTR_ID +
            " and ref.OBJECT_ID=OBJECTS.OBJECT_ID" +
            " and ref.REFERENCE=author.OBJECT_ID" +
            " and author.OBJECT_ID=?" +
            " and OBJECTS.OBJECT_TYPE_ID=" + DATA_VISUALIZATION_PROJECT_OBJTYPE_ID +
            " ORDER BY creation_date.date_value";
    private static final String SELECT_DV_PROJECTS_USER_HAVE_ACCESS_TO = "select objects.object_id id, objects.name name, creation_date.date_value creation_date,author.object_id author," +
            " description.value description" +
            " from OBJECTS, ATTRIBUTES creation_date,Objects author, ATTRIBUTES description, OBJREFERENCE ref" +
            " WHERE OBJECTS.OBJECT_ID=creation_date.object_id" +
            " AND creation_date.ATTR_id=" + PROJECT_DATE_ATTR_ID +
            " and OBJECTS.OBJECT_ID=description.object_id" +
            " and description.ATTR_ID=" + PROJECT_DESCRIPTION_ATTR_ID +
            " and ref.ATTR_ID=" + PROJECT_SHARED_RELATION_ATTR_ID +
            " and ref.OBJECT_ID=OBJECTS.OBJECT_ID" +
            " and ref.REFERENCE=author.OBJECT_ID" +
            " and author.OBJECT_ID=?" +
            " and OBJECTS.OBJECT_TYPE_ID=" + DATA_VISUALIZATION_PROJECT_OBJTYPE_ID +
            " ORDER BY creation_date.date_value";
    private static final String SELECT_PROJECT_GRAPHS = "select graph.object_id id, graph.name name, json.value json," +
            " average.value average, olimpic_average.value olimpic_average,math_expectation.value math_expectation, dispersion.value dispersion" +
            " from OBJECTS graph,OBJECTS project, ATTRIBUTES json, ATTRIBUTES average, ATTRIBUTES olimpic_average, " +
            " ATTRIBUTES math_expectation, ATTRIBUTES dispersion, OBJREFERENCE reference" +
            " where graph.OBJECT_ID=json.OBJECT_ID" +
            " and json.ATTR_ID=" + JSON_RESULT_ATTR_ID +
            " and graph.OBJECT_ID=average.OBJECT_ID" +
            " and average.ATTR_ID=" + AVERAGE_DVPROJECT_ATTR_ID +
            " and graph.OBJECT_ID=olimpic_average.OBJECT_ID" +
            " and olimpic_average.ATTR_ID=" + OLIMPIC_AVERAGE_DVPROJECT_ATTR_ID +
            " and graph.OBJECT_ID=math_expectation.OBJECT_ID" +
            " and math_expectation.ATTR_ID=11" + MATH_EXPECTATION_DVPROJECT_ATTR_ID +
            " and graph.OBJECT_ID=dispersion.OBJECT_ID" +
            " and dispersion.ATTR_ID=" + DISPERSION_DVPROJECT_ATTR_ID +
            " and reference.ATTR_ID=" + PROJECT_GRAPHICS_RELATION_ATTR_ID +
            " and reference.REFERENCE=graph.OBJECT_ID" +
            " and reference.OBJECT_ID=project.OBJECT_ID" +
            " and project.OBJECT_ID=?";


}
