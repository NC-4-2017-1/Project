package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.controllers.request.CreateUserRequest;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/admin-panel", method = RequestMethod.GET)
    public String adminDashboard(Model model) {
        return "adminDashboard";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    public String userDashboard(Model model) {
        return "userDashboard";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/create-user", method = RequestMethod.GET)
    public String createUser(Model model) {
        return "userCreation";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    @ResponseBody
    public User create(@RequestBody CreateUserRequest createUserRequest, Model model) {
        return userDAO.createUser(createUserRequest.getFirstName(), createUserRequest.getLastName(),
                createUserRequest.getEmail(), createUserRequest.getPassword(), UserTypes.REGULAR_USER);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteUser(User user,
                              Model model) {
        return userDAO.deleteUser(user);
    }

    @Secured("ROLE_ADMIN")
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




