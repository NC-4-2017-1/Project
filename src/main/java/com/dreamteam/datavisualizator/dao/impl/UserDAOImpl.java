package com.dreamteam.datavisualizator.dao.impl;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Collection;

public class UserDAOImpl implements UserDAO{

    public User getUserById(BigInteger id) {
        return null;
    }

    public User getUserByFullName(String fullname) {
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
}
