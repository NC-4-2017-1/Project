package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
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

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String adminDashboard(Model model) {
        return "adminDashboard";
    }

    @RequestMapping(path = "/regular-user", method = RequestMethod.GET)
    public String userDashboard(Model model) {
        return "userDashboard";
    }

    @RequestMapping(path = "/get-by-id", method = RequestMethod.GET)
    public User getUserById(Model model) {
        return null;
    }

    @RequestMapping(path = "/get-by-name", method = RequestMethod.GET)
    public User getUserByFullName(@RequestParam("fullName") String fullName,
                                  Model model) {
        return null;
    }

    @RequestMapping(path = "/get-by-email", method = RequestMethod.GET)
    public User getUserByEmail(@RequestParam("email") String email,
                               Model model) {
        return null;
    }

    @RequestMapping(path = "/update-email", method = RequestMethod.GET)
    public User updateUsersEmail(@RequestParam("email") String email,
                                 Model model) {
        return null;
    }

    @RequestMapping(path = "/update-name", method = RequestMethod.GET)
    public User updateUsersName(@RequestParam("firstName") String firstName,
                                @RequestParam("firstName") String lastName,
                                Model model) {
        return null;
    }

    @RequestMapping(path = "/update-password", method = RequestMethod.GET)
    public User updateUsersPassword(@RequestParam("password") String password,
                                    Model model) {
        return null;
    }

    @RequestMapping(path = "/access-to-project", method = RequestMethod.GET)
    @ResponseBody
    public boolean giveUserAccessToProject(User user, Project project,
                                           Model model) {
        return userDAO.giveUserAccessToProject(user, project);
    }

    @RequestMapping(path = "/creation", method = RequestMethod.GET)
    public String userCreation(Model model) {
        return "userCreation";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteUser(User user,
                              Model model) {
        return userDAO.deleteUser(user);
    }

}


