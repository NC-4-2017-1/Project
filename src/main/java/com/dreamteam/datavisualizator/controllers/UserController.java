package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.User;
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
    public String userCreation(Model model) {
        return "userCreation";
    }


    //low prior
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteUser(User user,
                              Model model) {
        return userDAO.deleteUser(user);
    }

    @RequestMapping(path = "/update-user", method = RequestMethod.GET)
    public User updateUsersEmail(@RequestParam("email") String email,
                                 Model model) {
        return null;
    }

}


