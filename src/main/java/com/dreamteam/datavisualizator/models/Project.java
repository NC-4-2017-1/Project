package com.dreamteam.datavisualizator.models;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


public interface Project {
    BigInteger getId();

    String getName();

    Date getCreationDate();

    ProjectTypes getType();

    BigInteger getAuthor();

    String getAuthorFullName();

    List<User> getUsersProjectAccessibleTo();

    String getDescription();

    void setId(BigInteger id);

    void setAuthor(BigInteger id);

    void setAuthorFullName(String fullName);

    void setUsersProjectAccessible(List<User> usersProjectAccessible);

    void setType(ProjectTypes type);

    void setDescription(String description);

    void setCreationDate(Date creationDate);

    void setName(String name);
}
