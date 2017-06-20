package com.dreamteam.datavisualizator.dao;

import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;

import java.math.BigInteger;
import java.util.List;

public interface UserDAO {
    User getUserById(BigInteger id);

    User getUserByEmail(String email);

    List<User> getAllUsersList(String field, String sortType, String whereEmail);

    boolean deleteUser(User user);

    User createUser(String firstName, String lastName, String email, String password, UserTypes type);

    boolean giveUserAccessToProject(BigInteger user, BigInteger project);

    boolean removeAccessToProjectFromUser(BigInteger userId, BigInteger projectId);

    List<User> getUsersThatHaveAccessToProject(BigInteger project, String whereEmail);

    List<Project> getAllUserProjects(User user, String field, String sortType, String whereName);

    List<Project> getAllSharedToUserProjects(User user, String field, String sortType, String whereName);
}
