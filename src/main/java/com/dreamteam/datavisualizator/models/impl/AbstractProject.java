package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.User;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public abstract class AbstractProject implements Project{
    private BigInteger id;
    private String name;
    private Date creationDate;
    private User author;
    private String description;
    private ProjectTypes type;
    private Collection<User> usersProjectAccessible;


    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor(){
        return author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setType(ProjectTypes type) {
        this.type = type;
    }

    public ProjectTypes getType(){
        return type;
    }

    public void setUsersProjectAccessible(Collection<User> usersProjectAccessible) {
        this.usersProjectAccessible = usersProjectAccessible;
    }

    public Collection<User> getUsersProjectAccessibleTo(){
        return usersProjectAccessible;
    }
}
