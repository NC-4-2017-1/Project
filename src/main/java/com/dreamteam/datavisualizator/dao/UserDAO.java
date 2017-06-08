package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;

import java.math.BigInteger;
import java.util.List;

public interface UserDAO {
    User getUserById(BigInteger id);

    User getUserByFullName(String fullName);

    User getUserByEmail(String email);

    List<User> getAllUsersList();

    boolean deleteUser(User user);

    User createUser(String firstName, String lastName, String email, String password, UserTypes type);

    User updateUsersEmail(User user, String email);

    User updateUsersName(User user, String first_name, String last_name);

    User updateUsersPassword(User user, String password);

    boolean giveUserAccessToProject(User user, Project project);

    boolean removeAccessToProjectFromUser(User user, Project project);

    List<User> getUsersThatHaveAccessToProject(Project project);

    List<Project> getAllUserProjects(User user);
}
