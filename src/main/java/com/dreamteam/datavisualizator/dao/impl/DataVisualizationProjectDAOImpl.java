package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DataVisualizationProjectDAOImpl implements DataVisualizationProjectDAO {
    private enum DVProjectColumnName {ID, NAME, CREATION_DATE, AUTHOR, DESCRIPTION}

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

    public boolean saveProject(Project project) {
        generalTemplate.update(INSERT_DV_PROJECT, project.getName(), project.getDescription(), project.getAuthor().toString());
        return true;
    }

    public List<Graphic> getProjectGraphics(Project project) {
        return null;
    }

    public void getPreviewProjectDataForUser(User user) {

    }

    private class DataVisualizationProjectRowMapper implements RowMapper {
        public DataVisualizationProject mapRow(ResultSet rs, int rownum) throws SQLException {
            DataVisualizationProject.Builder builder = new DataVisualizationProject.Builder(
                    BigInteger.valueOf(rs.getLong(DVProjectColumnName.ID.toString())),
                    rs.getString(DVProjectColumnName.NAME.toString()),
                    rs.getDate(DVProjectColumnName.CREATION_DATE.toString()),
                    BigInteger.valueOf(rs.getLong(DVProjectColumnName.AUTHOR.toString()))
            );

            builder.description(rs.getString(DVProjectColumnName.DESCRIPTION.toString()));
//            builder.usersProjectAccessible();
//            builder.graphics();
            return builder.build();
        }
    }

    private String INSERT_DV_PROJECT = "call insert_dv_project(?,?,?)";
}
