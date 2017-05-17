package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
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
import java.util.Collection;

import static com.dreamteam.datavisualizator.common.IdList.*;

@Repository("userDaoImpl")
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
        return generalTemplate.query(GET_ALL_USERS, new UserRowMapper());
    }

    public boolean deleteUser(User user) {
        return false; //TODO method for deleting user from database
    }

    @Transactional
    public User updateUsersEmail(User user, String email) {
        user.setEmail(email);
        generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, email, user.getId(), USER_EMAIL_ATTR_ID);
        return user;
    }

    public User updateUsersName(User user, String name) {
        return null; //TODO method for updating user name
    }

    public User updateUsersPassword(User user, String password) {
        user.setPassword(password);
        generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, password, user.getId(), PASSWORD_ATTR_ID);
        return user;
    }

    public boolean giveUserAccessToProject(User user, Project project) {
        return false; //TODO method for giving access to project
    }

    public boolean removeAccessToProjectFromUser(User user, Project project) {
        return false;//TODO method for removing access to project
    }

    public User authorizeUser(String email, String password) {
        return null;//TODO method for authorization
    }

    @Transactional
    public User createUser(String firstName, String lastName, String email, String password, UserTypes type) {
        User user = new UserImpl.Builder(email, password).firstName(firstName).lastName(lastName).type(type).build();
        BigInteger insertedObjectId = createUserObject(lastName + " " + firstName);
        generalTemplate.update(INSERT_USER_ATTR_VALUE, USER_EMAIL_ATTR_ID, insertedObjectId, email);
        generalTemplate.update(INSERT_USER_ATTR_VALUE, USER_FIRST_NAME_ATTR_ID, insertedObjectId, firstName);
        generalTemplate.update(INSERT_USER_ATTR_VALUE, USER_LAST_NAME_ATTR_ID, insertedObjectId, lastName);
        generalTemplate.update(INSERT_USER_ATTR_VALUE, PASSWORD_ATTR_ID, insertedObjectId, password);
        generalTemplate.update(INSERT_USER_ATTR_LIST_VALUE, USER_TYPE_ATTR_ID, insertedObjectId, UserTypes.REGULAR_USER.getId());
        return user;
    }

    private BigInteger createUserObject(String name) {
        simpleCallTemplate.withFunctionName(INSERT_OBJECT);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("obj_type_id", USER_OBJTYPE_ID)
                .addValue("obj_name", name);
        return simpleCallTemplate.executeFunction(BigDecimal.class, in).toBigInteger();
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

    private static final String INSERT_OBJECT = "insert_object";
    private static final String INSERT_USER_ATTR_VALUE = "insert into attributes(attr_id, object_id, value) values (?, ?, ?)";
    private static final String INSERT_USER_ATTR_LIST_VALUE = "insert into attributes(attr_id, object_id, list_value_id) values (?, ?, ?)";

    private static final String UPDATE_ATTRIBUTE_BY_USER_ID="update attributes" +
            " set value = ?" +
            " where object_id= ?" +
            " and attr_id= ?";

    private static final String SELECT_USER_BY_MAIL = "select obj_user.OBJECT_ID id, first_name.VALUE first_name, last_name.VALUE last_name" +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " + USER_OBJTYPE_ID.toString() + " " +
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + USER_FIRST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + USER_LAST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " + USER_EMAIL_ATTR_ID + " " +
            " and email.value=?";
    private static final String GET_ALL_USERS = "select obj_user.OBJECT_ID id, first_name.VALUE first_name, last_name.VALUE last_name, email.VALUE email " +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " + USER_OBJTYPE_ID.toString() + " " +
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + USER_FIRST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + USER_LAST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " + USER_EMAIL_ATTR_ID;
    private static final String SELECT_USER_BY_FULLNAME = "select obj_user.OBJECT_ID id,  first_name.VALUE first_name, last_name.VALUE last_name, email.VALUE email " +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " + USER_OBJTYPE_ID.toString() + " " +
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + USER_FIRST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + USER_LAST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " + USER_EMAIL_ATTR_ID + " " +
            " and obj_user.name=?";
    private static final String SELECT_USER_BY_ID = "select obj_user.OBJECT_ID id, first_name.VALUE first_name, last_name.VALUE last_name, email.VALUE email" +
            " from objects obj_user, ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES email " +
            " where obj_user.OBJECT_TYPE_ID = " + USER_OBJTYPE_ID.toString() + " " +
            " and obj_user.OBJECT_ID = first_name.OBJECT_ID " +
            " and first_name.ATTR_ID = " + USER_FIRST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = last_name.OBJECT_ID " +
            " and last_name.ATTR_ID = " + USER_LAST_NAME_ATTR_ID + " " +
            " and  obj_user.OBJECT_ID = email.OBJECT_ID " +
            " and email.ATTR_ID = " + USER_EMAIL_ATTR_ID + " " +
            " and obj_user.OBJECT_ID=?";

}
