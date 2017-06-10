package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.HealthMonitorProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import com.dreamteam.datavisualizator.models.impl.HealthMonitorProject;
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
import java.util.Date;
import java.util.List;

import static com.dreamteam.datavisualizator.common.IdList.*;

@Repository("userDaoImpl")
public class UserDAOImpl extends AbstractDAO implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    @Autowired
    private JdbcTemplate generalTemplate;
    @Autowired
    private HealthMonitorProjectDAO healthMonitorProjectDAO;
    @Autowired
    private DataVisualizationProjectDAO dataVisualizationProjectDAO;

    @Override
    public User getUserById(BigInteger id) {
        try {
            return generalTemplate.queryForObject(SELECT_USER_BY_ID, new Object[]{id}, new UserRowMapperWithoutPassword());
        } catch (DataAccessException e) {
            LOGGER.error("User not fetched by id " + id, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("User not fetched by id " + id, e);
            return null;
        }

    }

    @Override
    public User getUserByFullName(String fullName) {
        try {
            return generalTemplate.queryForObject(SELECT_USER_BY_FULLNAME, new Object[]{fullName}, new UserRowMapperWithoutPassword());
        } catch (DataAccessException e) {
            LOGGER.error("User not fetched by full name " + fullName, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("User not fetched by full name " + fullName, e);
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return generalTemplate.queryForObject(SELECT_USER_FOR_EMAIL_FOR_AUTHORIZATION, new Object[]{email}, new UserRowMapper());
        } catch (DataAccessException e) {
            LOGGER.debug("User not fetched by email for authorization" + email, e);
            return null;
        } catch (Exception e) {
            LOGGER.debug("User not fetched by email for authorization " + email, e);
            return null;
        }
    }

    @Override
    public List<User> getAllUsersList() {
        try {
            return generalTemplate.query(SELECT_ALL_USERS, new UserRowMapperWithoutPassword());
        } catch (DataAccessException e) {
            LOGGER.error("List of all users not fetched", e);
            return null;
        } catch (Exception e) {
            LOGGER.debug("List of all users not fetched", e);
            return null;
        }
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean deleteUser(User user) {
        try {
            List<HealthMonitorProject> hmProjects = healthMonitorProjectDAO.getProjectsByAuthor(user);
            List<DataVisualizationProject> dvProjects = dataVisualizationProjectDAO.getProjectsByAuthor(user);
            for (HealthMonitorProject hmProject : hmProjects) {
                healthMonitorProjectDAO.deleteProject(hmProject);
            }
            for (DataVisualizationProject dvProject : dvProjects) {
                dataVisualizationProjectDAO.deleteProject(dvProject);
            }
            generalTemplate.update(DELETE_OBJECT, user.getId());
            return true;
        } catch (DataAccessException e) {
            LOGGER.error("User (id: " + user.getId() + ", name: " + user.getFullName() + ") not removed", e);
            return false;
        } catch (Exception e) {
            LOGGER.error("User (id: " + user.getId() + ", name: " + user.getFullName() + ") not removed", e);
            return false;
        }
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public User updateUsersEmail(User user, String email) {
        try {
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, email, user.getId(), USER_EMAIL_ATTR_ID);
            user.setEmail(email);
        } catch (DataAccessException e) {
            LOGGER.error("User (id: " + user.getId() + ", name: " + user.getFullName() + ") not updated email to " + email, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("User (id: " + user.getId() + ", name: " + user.getFullName() + ") not updated email to " + email, e);
            return null;
        }
        return user;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public User updateUsersName(User user, String firstName, String lastName) {
        try {
            generalTemplate.update(UPDATE_USER_NAME, lastName + " " + firstName, user.getId());
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, firstName, user.getId(), USER_FIRST_NAME_ATTR_ID);
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, lastName, user.getId(), USER_LAST_NAME_ATTR_ID);
            user = getUserById(user.getId());
        } catch (DataAccessException e) {
            LOGGER.error("User (id: " + user.getId() + ", name: " + user.getFullName() + ") not updated full name to " + lastName + " " + firstName, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("User (id: " + user.getId() + ", name: " + user.getFullName() + ") not updated full name to " + lastName + " " + firstName, e);
            return null;
        }
        return user;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public User updateUsersPassword(User user, String password) {
        try {
            generalTemplate.update(UPDATE_ATTRIBUTE_BY_USER_ID, password, user.getId(), PASSWORD_ATTR_ID);
            user.setPassword(password);
        } catch (DataAccessException e) {
            LOGGER.error("User (id: " + user.getId() + ", name: " + user.getFullName() + ") not updated password to " + password, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("User (id: " + user.getId() + ", name: " + user.getFullName() + ") not updated password to " + password, e);
            return null;
        }
        return user;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean giveUserAccessToProject(User user, Project project) {
        try {
            if (user != null && project != null) {
                generalTemplate.update(INSERT_OBJREFERENCE_RELATION, PROJECT_SHARED_RELATION_ATTR_ID, user.getId(), project.getId());
            } else {
                LOGGER.error("Access to project was not given because of nulls in method");
                return false;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Access to project (id: " + project.getId() + ", name: " + project.getName() +
                    ") not granted to User (id: " + user.getId() + ", name: " + user.getFullName() + ")", e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Access to project (id: " + project.getId() + ", name: " + project.getName() +
                    ") not granted to User (id: " + user.getId() + ", name: " + user.getFullName() + ")", e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public boolean removeAccessToProjectFromUser(User user, Project project) {
        try {
            if (user != null && project != null) {
                generalTemplate.update(REMOVE_ACCESS_TO_PROJECT_FROM_USER, project.getId(), user.getId());
            } else {
                LOGGER.error("Access to project was not removed because of nulls in method");
                return false;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Access to project (id: " + project.getId() + ", name: " + project.getName() +
                    ") not removed from User (id: " + user.getId() + ", name: " + user.getFullName() + ")", e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Access to project (id: " + project.getId() + ", name: " + project.getName() +
                    ") not removed from User (id: " + user.getId() + ", name: " + user.getFullName() + ")", e);
        }
        return true;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = {DataAccessException.class, Exception.class})
    public User createUser(String firstName, String lastName, String email, String password, UserTypes type) {
        //!TODO check if email exists and don't let to create account if so
        User user;
        BigInteger insertedObjectId;
        try {
            insertedObjectId = createObject(lastName + " " + firstName, USER_OBJTYPE_ID);
            generalTemplate.update(INSERT_ATTR_VALUE, USER_EMAIL_ATTR_ID, insertedObjectId, email);
            generalTemplate.update(INSERT_ATTR_VALUE, USER_FIRST_NAME_ATTR_ID, insertedObjectId, firstName);
            generalTemplate.update(INSERT_ATTR_VALUE, USER_LAST_NAME_ATTR_ID, insertedObjectId, lastName);
            generalTemplate.update(INSERT_ATTR_VALUE, PASSWORD_ATTR_ID, insertedObjectId, password);
            generalTemplate.update(INSERT_ATTR_LIST_VALUE, USER_TYPE_ATTR_ID, insertedObjectId, UserTypes.REGULAR_USER.getId());

            user = getUserById(insertedObjectId);
        } catch (DataAccessException e) {
            LOGGER.error("User not created", e);
            return null;
        } catch (Exception e) {
            LOGGER.error("User not created", e);
            return null;
        }
        return user;
    }


    @Override
    public List<User> getUsersThatHaveAccessToProject(Project project) {
        try {
            if (project != null) {
                return generalTemplate.query(SELECT_USERS_THAT_HAVE_ACCESS_TO_PROJECT, new Object[]{project.getId()}, new UserRowMapperWithoutPassword());
            } else {
                LOGGER.error("Users that have access to project wasn't fetched because project is " + project);
                return null;
            }
        } catch (DataAccessException e) {
            LOGGER.error("Users that have access to project wasn't fetched project id:" + project.getId() + " name:" + project.getName(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Users that have access to project wasn't fetched project id:" + project.getId() + " name:" + project.getName(), e);
            return null;
        }
    }

    @Override
    public List<Project> getAllUserProjects(User user) {
        try {
            if (user != null) {
                return generalTemplate.query(SELECT_ALL_USERS_PROJECT, new Object[]{user.getId(), user.getId()}, new ProjectSimpleRowMapper());
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

    private class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rownum) throws SQLException {
            String email = rs.getString(UserColumnName.email.toString());
            BigInteger id = rs.getBigDecimal(UserColumnName.id.toString()).toBigInteger();
            String firstName = rs.getString(UserColumnName.firstName.toString());
            String lastName = rs.getString(UserColumnName.lastName.toString());
            String password = rs.getString(UserColumnName.password.toString());
            BigInteger role = rs.getBigDecimal(UserColumnName.usertype.toString()).toBigInteger();
            UserTypes userType = UserTypes.getRoleById(role);
            UserImpl.Builder builder = new UserImpl.Builder(email, password);
            builder.buildId(id);
            builder.buildFirstName(firstName);
            builder.buildLastName(lastName);
            builder.buildType(userType);
            return builder.buildUser();
        }
    }

    private class UserRowMapperWithoutPassword implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rownum) throws SQLException {
            String email = rs.getString(UserColumnName.email.toString());
            BigInteger id = rs.getBigDecimal(UserColumnName.id.toString()).toBigInteger();
            String firstName = rs.getString(UserColumnName.firstName.toString());
            String lastName = rs.getString(UserColumnName.lastName.toString());
            BigInteger role = rs.getBigDecimal(UserColumnName.usertype.toString()).toBigInteger();
            UserTypes userType = UserTypes.getRoleById(role);
            UserImpl.Builder builder = new UserImpl.Builder(email, null);
            builder.buildId(id);
            builder.buildFirstName(firstName);
            builder.buildLastName(lastName);
            builder.buildType(userType);
            return builder.buildUser();
        }
    }

    private class ProjectSimpleRowMapper implements RowMapper<Project> {
        @Override
        public Project mapRow(ResultSet resultSet, int rownum) throws SQLException {
            BigInteger id = resultSet.getBigDecimal(ProjectColumnName.id.toString()).toBigInteger();
            String name = resultSet.getString(ProjectColumnName.name.toString());
            BigInteger type_id = resultSet.getBigDecimal(ProjectColumnName.typeId.toString()).toBigInteger();
            Date creationDate = resultSet.getDate(ProjectColumnName.creationDate.toString());
            BigInteger author = resultSet.getBigDecimal(ProjectColumnName.author.toString()).toBigInteger();
            String authorName = resultSet.getString(ProjectColumnName.authorName.toString());
            String description = resultSet.getString(ProjectColumnName.description.toString());
            if (type_id.equals(BigInteger.valueOf(3L))) {
                HealthMonitorProject.Builder hmbuilder = new HealthMonitorProject.Builder(id, name, creationDate, description, author, authorName);
                return hmbuilder.buildProject();
            } else {
                DataVisualizationProject.Builder dvbuilder = new DataVisualizationProject.Builder(name, creationDate, author, authorName);
                dvbuilder.buildId(id);
                dvbuilder.buildDescription(description);
                return dvbuilder.buildProject();
            }
        }
    }

    private enum UserColumnName {
        id("id"),
        firstName("first_name"),
        lastName("last_name"),
        email("email"),
        password("password"),
        usertype("usertype");

        private final String columnName;

        UserColumnName(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private enum ProjectColumnName {
        id("id"),
        name("name"),
        typeId("type_id"),
        creationDate("creation_date"),
        author("author"),
        authorName("author_name"),
        description("description");

        private final String columnName;

        ProjectColumnName(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private static final String SELECT_ALL_USERS_PROJECT = "select id, name, type_id, creation_date, author,author_name, description from (" +
            " select project.object_id id, project.name name, project.object_type_id type_id, " +
            " creation_date.date_value creation_date, author.object_id author, author.name author_name, description.value description" +
            " from objects project, attributes creation_date, objects author, attributes description, objreference ref" +
            " where project.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and project.object_id = description.object_id" +
            " and description.attr_id = 7 " +
            " and ref.attr_id = 17" +
            " and ref.object_id = project.object_id" +
            " and ref.reference = author.object_id" +
            " and author.object_id=?" +
            " union" +
            " select project.object_id id, project.name name, project.object_type_id type_id," +
            " creation_date.date_value creation_date, author.object_id author,  author.name author_name,description.value description" +
            " from objects project, attributes creation_date, objects have_access, attributes description, " +
            " objects author, objreference ref_author, objreference ref_access" +
            " where project.object_id = creation_date.object_id" +
            " and creation_date.attr_id = 6" +
            " and project.object_id = description.object_id" +
            " and description.attr_id = 7" +
            " and ref_author.attr_id = 17" +
            " and ref_author.object_id = project.object_id" +
            " and ref_author.reference = author.object_id" +
            " and ref_access.attr_id = 18" +
            " and ref_access.object_id = project.object_id" +
            " and ref_access.reference = have_access.object_id" +
            " and have_access.object_id = ?" +
            " )order by creation_date desc";

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
    private static final String SELECT_USER_FOR_EMAIL_FOR_AUTHORIZATION = "select obj_user.object_id id, first_name.value first_name, last_name.value last_name, email.value email, password.value password, usertype.list_value_id usertype " +
            " from objects obj_user, attributes first_name,  attributes last_name,  attributes email, attributes password, attributes usertype " +
            " where obj_user.object_type_id = 1 " +
            " and obj_user.object_id = first_name.object_id " +
            " and first_name.attr_id = 2 " +
            " and  obj_user.object_id = last_name.object_id " +
            " and last_name.attr_id = 3 " +
            " and  obj_user.object_id = email.object_id " +
            " and obj_user.object_id = password.object_id " +
            " and password.attr_id = 4 " +
            " and usertype.attr_id = 5 " +
            " and obj_user.object_id = usertype.object_id " +
            " and email.attr_id = 1 " +
            " and email.value=?";
    private static final String SELECT_ALL_USERS = "select obj_user.object_id id, first_name.value first_name, last_name.value last_name, email.value email, usertype.list_value_id usertype " +
            " from objects obj_user, attributes first_name,  attributes last_name,  attributes email, attributes usertype " +
            " where obj_user.object_type_id = 1 " +
            " and obj_user.object_id = first_name.object_id " +
            " and first_name.attr_id = 2 " +
            " and  obj_user.object_id = last_name.object_id " +
            " and last_name.attr_id = 3 " +
            " and  obj_user.object_id = email.object_id " +
            " and email.attr_id = 1 " +
            " and obj_user.object_id = usertype.object_id " +
            " and usertype.list_value_id = 1";
    private static final String SELECT_USER_BY_FULLNAME = "select obj_user.object_id id,  first_name.value first_name, last_name.value last_name, email.value email, usertype.list_value_id usertype" +
            " from objects obj_user, attributes first_name,  attributes last_name,  attributes email, attributes usertype" +
            " where obj_user.object_type_id = 1" +
            " and obj_user.object_id = first_name.object_id" +
            " and first_name.attr_id = 2" +
            " and  obj_user.object_id = last_name.object_id" +
            " and last_name.attr_id = 3" +
            " and  obj_user.object_id = email.object_id" +
            " and email.attr_id = 1" +
            " and obj_user.object_id = usertype.object_id" +
            " and usertype.attr_id = 5" +
            " and usertype.list_value_id = 1" +
            " and obj_user.name = ?";
    private static final String SELECT_USER_BY_ID = "select obj_user.object_id id, first_name.value first_name, last_name.value last_name, email.value email, usertype.list_value_id usertype" +
            " from objects obj_user, attributes first_name,  attributes last_name,  attributes email, attributes usertype" +
            " where obj_user.object_type_id =1" +
            " and obj_user.object_id = first_name.object_id" +
            " and first_name.attr_id = 2" +
            " and obj_user.object_id = last_name.object_id" +
            " and last_name.attr_id = 3" +
            " and  obj_user.object_id = email.object_id" +
            " and email.attr_id = 1" +
            " and obj_user.object_id = usertype.object_id" +
            " and usertype.attr_id = 5" +
            " and usertype.list_value_id = 1" +
            " and obj_user.object_id = ?";
}
