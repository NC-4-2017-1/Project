package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;

import java.math.BigInteger;
import java.util.List;

public interface UserDAO {
    User getUserById(BigInteger id);

    User getUserByEmail(String email);

    List<User> getAllUsersList();

    boolean deleteUser(User user);

    User createUser(String firstName, String lastName, String email, String password, UserTypes type);

    boolean giveUserAccessToProject(User user, Project project);

    boolean removeAccessToProjectFromUser(User user, Project project);

    List<User> getUsersThatHaveAccessToProject(Project project);

    List<Project> getAllUserProjects(User user, int field, String sortType);

    List<Project> getAllSharedToUserProjects(User user, int field, String sortType);
}
