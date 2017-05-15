package com.dreamteam.datavisualizator.controllers;


import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.dao.impl.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController extends UserDAOImpl{
    @Autowired
    UserDAO userdao;

}
