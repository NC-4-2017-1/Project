package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
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
    private enum DVProjectColumnName {ID, NAME, CREATION_DATE, AUTHOR, DESCRIPTION}


    @Autowired
    private SimpleJdbcCall simpleCallTemplate;

    @Autowired
    private JdbcTemplate generalTemplate;

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

    @Transactional
    public Project saveProject(String name, BigInteger authorId, String description, List<Graphic> graphics) {
        Date projectCreationDate = new Date();
        Project project = new DataVisualizationProject.Builder(name, projectCreationDate, authorId).description(description).graphics(graphics).build();
        BigInteger insertedObjectId = createProjectObject("DV " + name);
        generalTemplate.update(INSERT_DV_PROJECT_ATTR_VALUE, PROJECT_NAME_ATTR_ID, insertedObjectId, name);
        generalTemplate.update(INSERT_DV_PROJECT_ATTR_VALUE, PROJECT_DESCRIPTION_ATTR_ID, insertedObjectId, description);
        generalTemplate.update(INSERT_DV_PROJECT_DATE_VALUE, PROJECT_DATE_ATTR_ID, insertedObjectId, projectCreationDate);

        generalTemplate.update(INSERT_DV_PROJECT_AUTHOR_RELATION, PROJECT_AUTHOR_RELATION_ATTR_ID , authorId, insertedObjectId);

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

    private class DataVisualizationProjectRowMapper implements RowMapper {
        public DataVisualizationProject mapRow(ResultSet rs, int rownum) throws SQLException {
            DataVisualizationProject.Builder builder = new DataVisualizationProject.Builder(
                    rs.getString(DVProjectColumnName.NAME.toString()),
                    rs.getDate(DVProjectColumnName.CREATION_DATE.toString()),
                    BigInteger.valueOf(rs.getLong(DVProjectColumnName.AUTHOR.toString()))
            );
            builder.id(BigInteger.valueOf(rs.getLong(DVProjectColumnName.ID.toString())));
            builder.description(rs.getString(DVProjectColumnName.DESCRIPTION.toString()));
//            builder.usersProjectAccessible();
//            builder.graphics();
            return builder.build();
        }
    }

    private static final String INSERT_OBJECT = "insert_object";
    private static final String INSERT_DV_PROJECT_ATTR_VALUE = "insert into attributes(attr_id, object_id, value) values (?, ?, ?)";
    private static final String INSERT_DV_PROJECT_DATE_VALUE = "insert into attributes(attr_id, object_id, date_value) values (?, ?, ?)";
    private static final String INSERT_DV_PROJECT_AUTHOR_RELATION = "insert into objreference(attr_id, reference, object_id) values (?, ?, ?)";
}
