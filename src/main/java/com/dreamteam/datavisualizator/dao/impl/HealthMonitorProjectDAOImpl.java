package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.common.SQL_Query_Constants;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.impl.HealthMonitorProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class HealthMonitorProjectDAOImpl implements HealthMonitorProjectDAO {

    @Autowired
    private JdbcTemplate generalTemplate;

    public Graphic getProjectGraphic(Project project) {
        return null;
    }

    public List<Selector> getProjectSelectors(Project project) {
        return null;
    }

    public Map<String, String> getProjectConnectionParameters(Project project) {
        return null;
    }

    public List<Date> getDataForGraphic(Map<String, String> map) {
        return null;
    }

    public List<Date> getDataForSelector(Map<String, String> map) {
        return null;
    }

    public boolean saveGraphic(Map<String, String> map, Graphic graphic) {
        return false;
    }

    public boolean saveSelector(Map<String, String> map, Selector selector) {
        return false;
    }

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
        generalTemplate.update(SQL_Query_Constants.INSERT_HM_PROJECT, project.getName(), project.getDescription(), project.getAuthor().toString());
        return true;
    }

    public void getPreviewProjectDataForUser(User user) {

    }

    private class HealthMonitorProjectRowMapper implements RowMapper {
        public HealthMonitorProject mapRow(ResultSet rs, int rownum) throws SQLException {
            HealthMonitorProject.Builder builder = new HealthMonitorProject.Builder(
                    rs.getString("name"),
                    rs.getDate("creationDate"),
                    new BigInteger(rs.getObject("author").toString())
            );
            builder.description(rs.getString("description"));

            return builder.build();
        }
    }
}
