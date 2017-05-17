package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.common.IdList;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;

@Repository
public class UserDAOImpl implements UserDAO {
    private enum UserColumnName {ID, FIRST_NAME, LAST_NAME, EMAIL}

    @Autowired
    private JdbcTemplate generalTemplate;

    @Autowired
    private SimpleJdbcCall simpleCallTemplate;

    public User getUserById(BigInteger id) {
        return generalTemplate.queryForObject(SELECT_USER_BY_ID, new Object[]{id}, new UserRowMapper());
    }

    public User getUserByFullName(String fullName) {
        return generalTemplate.queryForObject(SELECT_USER_BY_FULLNAME, new Object[]{fullName}, new UserRowMapper());
    }

    public User getUserByEmail(String email) {
        return generalTemplate.queryForObject(SELECT_USER_BY_MAIL, new Object[]{email}, new UserRowMapper());
    }

    public Collection<User> getAllUsersList() {
        return null;
    }

    public boolean deleteUser(User user) {
        return false;
    }

    public void createUser(String firstName, String lastName, String email, String password) {
        simpleCallTemplate.withProcedureName(INSERT_USER);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("u_email", email)
                .addValue("u_first_name", firstName)
                .addValue("u_last_name", lastName)
                .addValue("u_password", password);
        simpleCallTemplate.execute(in);
    }

    public User updateUsersEmail(User user, String email) {
        return null;
    }

    public User updateUsersName(User user, String name) {
        return null;
    }

    public User updateUsersPassword(User user, String password) {
        return null;
    }

    public boolean giveUserAccessToProject(User user, Project project) {
        return false;
    }

    public boolean removeAccessToProjectFromUser(User user, Project project) {
        return false;
    }

    public User authorizeUser(String email, String password) {
        return null;
    }

    private class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rownum) throws SQLException {
            UserImpl.Builder builder = new UserImpl.Builder(rs.getString(UserColumnName.EMAIL.toString()), null);
            builder.buildId(BigInteger.valueOf(rs.getLong(UserColumnName.ID.toString())));
            builder.firstName(rs.getString(UserColumnName.FIRST_NAME.toString()));
            builder.lastName(rs.getString(UserColumnName.LAST_NAME.toString()));
            builder.type(UserTypes.REGULAR_USER);
            return builder.build();
        }
    }

    private String INSERT_USER = "call insert_user(?,?,?,?)";
    private String SELECT_USER_BY_MAIL = "select obj_user.OBJECT_ID id, first_name.VALUE first_name, last_name.VALUE last_name" +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " + IdList.USER_OBJTYPE_ID.toString() + " " +
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + IdList.USER_FIRST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + IdList.USER_LAST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " + IdList.USER_EMAIL_ATTR_ID + " " +
            " and email.value=?";
    private String GET_ALL_USERS = "";
    private String SELECT_USER_BY_FULLNAME = "select obj_user.OBJECT_ID id,  first_name.VALUE first_name, last_name.VALUE last_name, email.VALUE email " +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " + IdList.USER_OBJTYPE_ID.toString() + " " +
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + IdList.USER_FIRST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + IdList.USER_LAST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " + IdList.USER_EMAIL_ATTR_ID + " " +
            " and obj_user.name=?";
    private String SELECT_USER_BY_ID = "select obj_user.OBJECT_ID id, first_name.VALUE first_name, last_name.VALUE last_name, email.VALUE email" +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " + IdList.USER_OBJTYPE_ID.toString() + " " +
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + IdList.USER_FIRST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + IdList.USER_LAST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " + IdList.USER_EMAIL_ATTR_ID + " " +
            " and obj_user.OBJECT_ID=?";

}
