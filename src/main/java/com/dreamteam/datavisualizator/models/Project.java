package com.dreamteam.datavisualizator.models;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;


public interface Project {
    BigInteger getId();
    String getName();
    Date getCreationDate();
    ProjectTypes getType();
    BigInteger getAuthor();
    Collection<User> getUsersProjectAccessibleTo();
    String getDescription();
}
