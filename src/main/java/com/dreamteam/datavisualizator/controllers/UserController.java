package com.dreamteam.datavisualizator.controllers;


import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(path = "/deleteUser", method = RequestMethod.GET)
    public void deleteUser(User user,
                           Model model) {
        userDAO.deleteUser(user);
    }

}
