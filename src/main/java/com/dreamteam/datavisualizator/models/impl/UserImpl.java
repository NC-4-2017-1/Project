package com.dreamteam.datavisualizator.models.impl;


import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;

import java.math.BigInteger;
import java.util.List;

public class UserImpl implements User{
    private BigInteger id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Project> userProjects;
    private List<Project> userHaveAccessToProjects;
    private UserTypes type;

    public static class Builder{
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private List<Project> userProjects;
        private List<Project> userHaveAccessToProjects;
        private UserTypes type;

        public Builder(String email, String password){
            this.email = email;
            this.password = password;
        }
        public Builder firstName(String val){
            firstName = val;
            return this;
        }
        public Builder lastName(String val){
            lastName = val;
            return this;
        }
        public Builder userProjects(List<Project> val){
            userProjects = val;
            return this;
        }
        public Builder userHaveAccessToProjects(List<Project> val){
            userHaveAccessToProjects = val;
            return this;
        }
        public Builder type(UserTypes  val){
            type = val;
            return this;
        }

        public UserImpl build(){
            return new UserImpl(this);
        }
    }

    private UserImpl(Builder builder){
        firstName = builder.firstName;
        lastName = builder.lastName;
        email = builder.email;
        password = builder.password;
        userProjects = builder.userProjects;
        userHaveAccessToProjects = builder.userHaveAccessToProjects;
        type = builder.type;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId(){
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setUserProjects(List<Project> userProjects) {
        this.userProjects = userProjects;
    }

    public List<Project> getUserAuthorProjects(){
        return userProjects;
    }

    public void setUserHaveAccessToProjects(List<Project> userHaveAccessToProjects) {
        this.userHaveAccessToProjects = userHaveAccessToProjects;
    }

    public List<Project> getUserHaveAccessProjects(){
        return userHaveAccessToProjects;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }

    public UserTypes getUserType(){
        return type;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

}
