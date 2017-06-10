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
}
