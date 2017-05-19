package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    private enum UserColumnName {
        id("id"),
        firstName("first_name"),
        lastName("last_name"),
        email("email");
        private final String columnName;

        private UserColumnName(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }


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

    @Transactional(transactionManager = "transactionManager", rollbackFor = DataAccessException.class)
    public User updateUsersEmail(User user, String email) {
        user.setEmail(email);
        try {
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, email, user.getId(), USER_EMAIL_ATTR_ID);
        } catch (DataAccessException e) {
            //TODO message to logger
            return null;
        }
        return user;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = DataAccessException.class)
    public User updateUsersName(User user, String firstName, String lastName) {
        try {
            generalTemplate.update(UPDATE_USER_NAME, firstName + " " + lastName, user.getId());
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, firstName, user.getId(), USER_FIRST_NAME_ATTR_ID);
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, lastName, user.getId(), USER_LAST_NAME_ATTR_ID);
            user = getUserById(user.getId());
        } catch (DataAccessException e) {
            //TODO message to legger
            return null;
        }
        return user;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = DataAccessException.class)
    public User updateUsersPassword(User user, String password) {
        user.setPassword(password);
        generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, password, user.getId(), PASSWORD_ATTR_ID);
        return user;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = DataAccessException.class)
    public boolean giveUserAccessToProject(User user, Project project) {
        try {
            generalTemplate.update(GIVE_USER_ACESS_TO_PROJECT, user.getId(), project.getId());
        } catch (DataAccessException e) {
            //TODO message to Logger
            return false;
        }
        return true;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = DataAccessException.class)
    public boolean removeAccessToProjectFromUser(User user, Project project) {
        try {
            generalTemplate.update(REMOVE_ACCESS_TO_PROJECT_FROM_USER, project.getId(), user.getId());
        } catch (DataAccessException e) {
            //TODO message to Logger
            return false;
        }
        return true;
    }

    public User authorizeUser(String email, String password) {
        return generalTemplate.queryForObject(AUTORIZE_USER_BY_LOGIN_N_PASSWORD, new Object[]{email, password}, new UserRowMapper());
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = DataAccessException.class)
    public User createUser(String firstName, String lastName, String email, String password, UserTypes type) {
        User user = new UserImpl.Builder(email, password).firstName(firstName).lastName(lastName).type(type).build();
        try {
            BigInteger insertedObjectId = createUserObject(lastName + " " + firstName);
            generalTemplate.update(INSERT_USER_ATTR_VALUE, USER_EMAIL_ATTR_ID, insertedObjectId, email);
            generalTemplate.update(INSERT_USER_ATTR_VALUE, USER_FIRST_NAME_ATTR_ID, insertedObjectId, firstName);
            generalTemplate.update(INSERT_USER_ATTR_VALUE, USER_LAST_NAME_ATTR_ID, insertedObjectId, lastName);
            generalTemplate.update(INSERT_USER_ATTR_VALUE, PASSWORD_ATTR_ID, insertedObjectId, password);
            generalTemplate.update(INSERT_USER_ATTR_LIST_VALUE, USER_TYPE_ATTR_ID, insertedObjectId, UserTypes.REGULAR_USER.getId());
        } catch (DataAccessException e) {
            //TODO message to logger
            return null;
        }
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
            UserImpl.Builder builder = new UserImpl.Builder(rs.getString(UserColumnName.email.toString()), null);
            builder.buildId(BigInteger.valueOf(rs.getLong(UserColumnName.id.toString())));
            builder.firstName(rs.getString(UserColumnName.firstName.toString()));
            builder.lastName(rs.getString(UserColumnName.lastName.toString()));
            builder.type(UserTypes.REGULAR_USER);
            return builder.build();
        }
    }

    private static final String INSERT_OBJECT = "insert_object";
    private static final String UPDATE_USER_NAME = "UPDATE objects" +
            " SET objects.name=?" +
            " WHERE objects.object_id=?";
    private static final String INSERT_USER_ATTR_VALUE = "INSERT INTO attributes(attr_id, object_id, value) VALUES (?, ?, ?)";
    private static final String INSERT_USER_ATTR_LIST_VALUE = "INSERT INTO attributes(attr_id, object_id, list_value_id) VALUES (?, ?, ?)";
    private static final String GIVE_USER_ACESS_TO_PROJECT = "insert into OBJREFERENCE(ATTR_ID,REFERENCE,OBJECT_ID)" +
            " VALUES (" + PROJECT_SHARED_RELATION_ATTR_ID + ",?,?)";//first userId, second projectId
    private static final String REMOVE_ACCESS_TO_PROJECT_FROM_USER = "delete from OBJREFERENCE" +
            " where OBJECT_ID=?" + //FIRST PROJECTiD
            " and REFERENCE=?" + //second userId
            " and ATTR_ID=" + PROJECT_SHARED_RELATION_ATTR_ID;
    private static final String UPDATE_ATTRIBUTE_BY_USER_ID = "UPDATE attributes" +
            " SET value = ?" +
            " WHERE object_id= ?" +
            " AND attr_id= ?";
    private static final String AUTORIZE_USER_BY_LOGIN_N_PASSWORD = "select users.object_id id, email.value email, first_name.value first_name, last_name.value last_name, password.value password" +
            " from objects users,  ATTRIBUTES email,  ATTRIBUTES first_name,  ATTRIBUTES last_name,  ATTRIBUTES password" +
            " where users.object_id=email.object_id" +
            " and email.attr_id=" + USER_EMAIL_ATTR_ID +
            " and  users.object_id=first_name.object_id" +
            " and first_name.attr_id=" + USER_FIRST_NAME_ATTR_ID +
            " and  users.object_id=last_name.object_id" +
            " and last_name.attr_id=" + USER_LAST_NAME_ATTR_ID +
            " and  users.object_id=password.object_id" +
            " and password.attr_id=" + PASSWORD_ATTR_ID +
            " and email.value=?" +
            " and password.value = ?";
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
            " and email.ATTR_ID = " + USER_EMAIL_ATTR_ID +
            " and obj_user.OBJECT_ID!=1";
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
