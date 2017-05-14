package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import com.dreamteam.datavisualizator.models.impl.UserImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class UserDAOImpl implements UserDAO {

    public User getUserById(BigInteger id) {
        return null;
    }

    public User getUserByFullName(String fullName) {
        return null;
    }

    public User getUserByEmail(String email) {
        return null;
    }

    public Collection<User> getAllUsersList() {
        return null;
    }

    public boolean deleteUser(User user) {
        return false;
    }

    public User createUser(String firstName, String lastName, String email, String password) {
        return null;
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

    private class UserRowMapper implements RowMapper {
        public User mapRow(ResultSet rs, int rownum) throws SQLException {
            UserImpl.Builder builder = new UserImpl.Builder(
                    rs.getString("email"),
                    rs.getString("password")
            );
            builder.firstName(rs.getString("first_name"));
            builder.lastName(rs.getString("last_name"));
            String type = rs.getString("type");
            builder.type(UserTypes.ADMIN.toString().equals(type) ? UserTypes.ADMIN : UserTypes.REGULAR_USER.toString().equals(type) ? UserTypes.REGULAR_USER : null);
            return builder.build();
        }
    }
}
