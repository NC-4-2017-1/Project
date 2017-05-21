package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static com.dreamteam.datavisualizator.common.IdList.*;

@Repository("userDaoImpl")
public class UserDAOImpl extends AbstractDAO implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    @Autowired
    private JdbcTemplate generalTemplate;

    public User getUserById(BigInteger id) {
        try {
            return generalTemplate.queryForObject(SELECT_USER_BY_ID, new Object[]{id}, new UserRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("User not fetched", e);
            return null;
        }
    }

    public User getUserByFullName(String fullName) {
        try {
            return generalTemplate.queryForObject(SELECT_USER_BY_FULLNAME, new Object[]{fullName}, new UserRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("User not fetched", e);
            return null;
        }
    }

    public User getUserByEmail(String email) {
        try {
            return generalTemplate.queryForObject(SELECT_USER_BY_MAIL, new Object[]{email}, new UserRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("User not fetched", e);
            return null;
        }
    }

    public Collection<User> getAllUsersList() {
        try {
            return generalTemplate.query(GET_ALL_USERS, new UserRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("Users not fetched", e);
            return null;
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean deleteUser(User user) {
        try {
            //TODO method for deleting user from database
        } catch (DataAccessException e) {
            LOGGER.error("User not removed", e);
            return false;
        } catch (Exception e){
            LOGGER.error("User not removed", e);
            return false;
        }
        return false;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public User updateUsersEmail(User user, String email) {
        user.setEmail(email);
        try {
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, email, user.getId(), USER_EMAIL_ATTR_ID);
        } catch (DataAccessException e) {
            LOGGER.error("User not updated", e);
            return null;
        } catch (Exception e){
            LOGGER.error("User not updated", e);
            return null;
        }
        return user;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public User updateUsersName(User user, String firstName, String lastName) {
        try {
            generalTemplate.update(UPDATE_USER_NAME, firstName + " " + lastName, user.getId());
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, firstName, user.getId(), USER_FIRST_NAME_ATTR_ID);
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, lastName, user.getId(), USER_LAST_NAME_ATTR_ID);
            user = getUserById(user.getId());
        } catch (DataAccessException e) {
            LOGGER.error("User not updated", e);
            return null;
        } catch (Exception e){
            LOGGER.error("User not updated", e);
            return null;
        }
        return user;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public User updateUsersPassword(User user, String password) {
        try {
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, password, user.getId(), PASSWORD_ATTR_ID);
            user.setPassword(password);
        } catch (DataAccessException e) {
            LOGGER.error("User not updated", e);
            return null;
        } catch (Exception e){
            LOGGER.error("User not updated", e);
            return null;
        }
        return user;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean giveUserAccessToProject(User user, Project project) {
        try {
            generalTemplate.update(INSERT_OBJREFERENCE_RELATION, PROJECT_SHARED_RELATION_ATTR_ID, user.getId(), project.getId());
        } catch (DataAccessException e) {
            LOGGER.error("Access not granted", e);
            return false;
        } catch (Exception e){
            LOGGER.error("Access not granted", e);
            return false;
        }
        return true;
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean removeAccessToProjectFromUser(User user, Project project) {
        try {
            generalTemplate.update(REMOVE_ACCESS_TO_PROJECT_FROM_USER, project.getId(), user.getId());
        } catch (DataAccessException e) {
            LOGGER.error("Access not removed", e);
            return false;
        } catch (Exception e){
            LOGGER.error("Access not removed", e);
            return false;
        }
        return true;
    }

    public User authorizeUser(String email, String password) {
        try {
            return generalTemplate.queryForObject(AUTORIZE_USER_BY_LOGIN_AND_PASSWORD, new Object[]{email, password}, new UserRowMapper());
        } catch (DataAccessException e) {
            LOGGER.error("User not authorized");
            return null;
        }
    }

    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public User createUser(String firstName, String lastName, String email, String password, UserTypes type) {
        User user = new UserImpl.Builder(email, password).buildFirstName(firstName).buildLastName(lastName).buildType(type).buildUser();
        try {
            BigInteger insertedObjectId = createObject(lastName + " " + firstName, USER_OBJTYPE_ID);
            generalTemplate.update(INSERT_ATTR_VALUE, USER_EMAIL_ATTR_ID, insertedObjectId, email);
            generalTemplate.update(INSERT_ATTR_VALUE, USER_FIRST_NAME_ATTR_ID, insertedObjectId, firstName);
            generalTemplate.update(INSERT_ATTR_VALUE, USER_LAST_NAME_ATTR_ID, insertedObjectId, lastName);
            generalTemplate.update(INSERT_ATTR_VALUE, PASSWORD_ATTR_ID, insertedObjectId, password);
            generalTemplate.update(INSERT_ATTR_LIST_VALUE, USER_TYPE_ATTR_ID, insertedObjectId, UserTypes.REGULAR_USER.getId());
        } catch (DataAccessException e) {
            LOGGER.error("User not created", e);
            return null;
        } catch (Exception e){
            LOGGER.error("User not created", e);
            return null;
        }
        return user;
    }

    private class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rownum) throws SQLException {
            String email = rs.getString(UserColumnName.email.toString());
            BigInteger id = BigInteger.valueOf(rs.getLong(UserColumnName.id.toString()));
            String firstName = rs.getString(UserColumnName.firstName.toString());
            String lastName = rs.getString(UserColumnName.lastName.toString());
            UserImpl.Builder builder = new UserImpl.Builder(email, null);
            builder.buildId(id);
            builder.buildFirstName(firstName);
            builder.buildLastName(lastName);
            builder.buildType(UserTypes.REGULAR_USER);
            return builder.buildUser();
        }
    }

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

    private static final String UPDATE_USER_NAME = "update objects" +
            " set objects.name=?" +
            " where objects.object_id=?";
    private static final String REMOVE_ACCESS_TO_PROJECT_FROM_USER = "delete from objreference" +
            " where object_id = ?" +
            " and reference = ?" +
            " and attr_id = 18";
    private static final String UPDATE_ATTRIBUTE_BY_USER_ID = "update attributes" +
            " set value = ?" +
            " where object_id= ?" +
            " and attr_id = ?";
    private static final String AUTORIZE_USER_BY_LOGIN_AND_PASSWORD = "select users.object_id id, email.value email, first_name.value first_name, last_name.value last_name, password.value password" +
            " from objects users,  attributes email,  attributes first_name,  attributes last_name,  attributes password" +
            " where users.object_id = email.object_id" +
            " and email.attr_id = 1" +
            " and  users.object_id = first_name.object_id" +
            " and first_name.attr_id = 2" +
            " and  users.object_id = last_name.object_id" +
            " and last_name.attr_id = 3" +
            " and  users.object_id = password.object_id" +
            " and password.attr_id = 4" +
            " and email.value = ?" +
            " and password.value = ?";
    private static final String SELECT_USER_BY_MAIL = "select obj_user.object_id id, first_name.value first_name, last_name.value last_name" +
            " from objects obj_user, attributes first_name,  attributes last_name,  attributes email" +
            " where obj_user.object_type_id = 1" +
            " and obj_user.object_id = first_name.object_id" +
            " and first_name.attr_id = 2" +
            " and  obj_user.object_id = last_name.object_id" +
            " and last_name.attr_id = 3" +
            " and  obj_user.object_id = email.object_id" +
            " and email.attr_id = 1" +
            " and email.value = ?";
    private static final String GET_ALL_USERS = "select obj_user.object_id id, first_name.value first_name, last_name.value last_name, email.value email" +
            " from objects obj_user, attributes first_name,  attributes last_name,  attributes email" +
            " where obj_user.object_type_id = 1" +
            " and obj_user.object_id = first_name.object_id" +
            " and first_name.attr_id = 2" +
            " and  obj_user.object_id = last_name.object_id" +
            " and last_name.attr_id = 3" +
            " and  obj_user.object_id = email.object_id" +
            " and email.attr_id = 1" +
            " and obj_user.object_id! = 1";
    private static final String SELECT_USER_BY_FULLNAME = "select obj_user.object_id id,  first_name.value first_name, last_name.value last_name, email.value email" +
            " from objects obj_user, attributes first_name,  attributes last_name,  attributes email" +
            " where obj_user.object_type_id = 1" +
            " and obj_user.object_id = first_name.object_id" +
            " and first_name.attr_id = 2" +
            " and  obj_user.object_id = last_name.object_id" +
            " and last_name.attr_id = 3" +
            " and  obj_user.object_id = email.object_id" +
            " and email.attr_id = 1" +
            " and obj_user.name = ?";
    private static final String SELECT_USER_BY_ID = "select obj_user.object_id id, first_name.value first_name, last_name.value last_name, email.value email" +
            " from objects obj_user, attributes first_name,  attributes last_name,  attributes email" +
            " where obj_user.object_type_id =1" +
            " and obj_user.object_id = first_name.object_id" +
            " and first_name.attr_id = 2" +
            " and obj_user.object_id = last_name.object_id" +
            " and last_name.attr_id = 3" +
            " and  obj_user.object_id = email.object_id" +
            " and email.attr_id = 1" +
            " and obj_user.object_id = ?";

}
