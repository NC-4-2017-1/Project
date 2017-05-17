package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(path = "/deleteUser", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteUser(User user,
                              Model model) {
        return userDAO.deleteUser(user);
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String adminDashboard(Model model) {
        return "adminDashboard";
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String userDashboard(Model model) {
        return "userDashboard";
    }

    @RequestMapping(path = "/creation", method = RequestMethod.GET)
    public String userCreation(Model model) {
        return "userCreation";
    }

}
