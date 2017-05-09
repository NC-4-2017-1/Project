package com.dreamteam.datavisualizator.models;


import java.math.BigInteger;
import java.util.List;

public interface User {
    BigInteger getId();
    String getFullName();
    String getFirstName();
    String getLastName();
    String getEmail();
    List<Project> getUserAuthorProjects();
    List<Project> getUserHaveAccessProjects();
    UserTypes getUserType();
    String getPassword();
}
