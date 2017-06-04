package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserRequest;
import com.dreamteam.datavisualizator.models.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/admin-panel", method = RequestMethod.GET)
    public String adminDashboard(Model model) {
        List<User> users = userDAO.getAllUsersList();
        model.addAttribute("users", users);
        return "adminDashboard";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    public String userDashboard() {
        return "userDashboard";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/create-user", method = RequestMethod.GET)
    public String createUser(@ModelAttribute("user") UserRequest userRequest) {
        return "userCreation";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") UserRequest userRequest) {
        userDAO.createUser(userRequest.getFirstName(), userRequest.getLastName(),
                userRequest.getEmail(), userRequest.getPassword(), UserTypes.REGULAR_USER);
        return "redirect:/user/admin-panel";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable BigInteger id) {
        User user = userDAO.getUserById(id);
        userDAO.deleteUser(user);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/update-user", method = RequestMethod.GET)
    public User updateUser(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email) {
        return null;
    }

}




