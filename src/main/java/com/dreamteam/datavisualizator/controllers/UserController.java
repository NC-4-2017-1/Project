package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(path = "/admin-panel", method = RequestMethod.GET)
    public String adminDashboard(Model model) {
        return "adminDashboard";
    }

    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    public String userDashboard(Model model) {
        return "userDashboard";
    }

    @RequestMapping(path = "/create-user", method = RequestMethod.GET)
    public String createUser(Model model) {
        return "userCreation";
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    @ResponseBody
    public User create(@RequestParam("firstName") String firstName,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("email") String email,
                       @RequestParam("password") String password,
                       Model model) {
        return userDAO.createUser(firstName, lastName, email, password, UserTypes.REGULAR_USER);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteUser(User user,
                              Model model) {
        return userDAO.deleteUser(user);
    }

    @RequestMapping(path = "/update-user", method = RequestMethod.GET)
    @ResponseBody
    public User updateUsers(@RequestParam("firstName") String firstName,
                            @RequestParam("lastName") String lastName,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            Model model) {
        return null;
    }
}




