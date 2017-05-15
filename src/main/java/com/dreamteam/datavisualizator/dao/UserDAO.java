package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Collection;

public interface UserDAO {
    User getUserById(BigInteger id);
    User getUserByFullName(String fullName);
    User getUserByEmail(String email);
    Collection<User> getAllUsersList();
    boolean deleteUser(User user);
    void createUser(String firstName, String lastName, String email, String password);
    User updateUsersEmail(User user,String email);
    User updateUsersName(User user, String name);
    User updateUsersPassword(User user, String password);
    boolean giveUserAccessToProject(User user, Project project);
    boolean removeAccessToProjectFromUser(User user, Project project);
    User authorizeUser(String email, String password);


}
