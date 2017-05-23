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

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String adminDashboard(Model model) {
        return "adminDashboard";
    }

    @RequestMapping(path = "/regularUser", method = RequestMethod.GET)
    public String userDashboard(Model model) {
        return "userDashboard";
    }

    @RequestMapping(path = "/creation", method = RequestMethod.GET)
    public String userCreation(Model model) {
        return "userCreation";
    }

    @RequestMapping(path = "/updateEmail", method = RequestMethod.GET)
    public User updateUsersEmail(User user,
                                 Model model) {
        return null;
    }

    @RequestMapping(path = "/updateName", method = RequestMethod.GET)
    public User updateUsersName(User user,
                                Model model) {
        return null;
    }

    @RequestMapping(path = "/updatePassword", method = RequestMethod.GET)
    public User updateUsersPassword(User user,
                                    Model model) {
        return null;
    }

    @RequestMapping(path = "/getById", method = RequestMethod.GET)
    public User getUserById(Model model) {
        return null;
    }

    @RequestMapping(path = "/getByFullName", method = RequestMethod.GET)
    public User getUserByFullName(Model model) {
        return null;
    }

    @RequestMapping(path = "/getByEmail", method = RequestMethod.GET)
    public User getUserByEmail(Model model) {
        return null;
    }


    @RequestMapping(path = "/deleteUser", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteUser(User user,
                              Model model) {
        return userDAO.deleteUser(user);
    }


}
