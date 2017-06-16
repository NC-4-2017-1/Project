package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserRequest;
import com.dreamteam.datavisualizator.models.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

import static com.dreamteam.datavisualizator.common.util.UserRequestValidator.isUserRequestValid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/admin-panel/{field}/{sortType}", method = RequestMethod.GET)
    public String getAdminDashboard(Model model, @PathVariable int field,@PathVariable String sortType) {
        if(!"desc".equals(sortType) && !"asc".equals(sortType)) {
            sortType = "asc";
        }
        if (field != 2 && field != 3 && field != 4){
            field = 3;
        }
        model.addAttribute("sortF",field);
        model.addAttribute("sortT",sortType);
        List<User> users = userDAO.getAllUsersList(field, sortType);
        model.addAttribute("users", users);
        return "adminDashboard";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/dashboard-get/{field}/{sortType}/{sortTab}", method = RequestMethod.GET)
    public String getUserDashboard(@PathVariable int field,@PathVariable String sortType, @PathVariable int sortTab, HttpServletRequest request, Model model) {
        if (sortTab != 1 && sortTab != 2){
            sortTab = 1;
        }
        if(!"desc".equals(sortType) && !"asc".equals(sortType)) {
            sortType = "desc";
        }
        if (sortTab == 1 && (field != 2 && field != 4)){
            field = 4;
        }
        if (sortTab == 2 && (field != 2 && field != 4 && field != 6)){
            field = 4;
        }
        model.addAttribute("sortF",field);
        model.addAttribute("sortT",sortType);
        model.addAttribute("sortTab",sortTab);
        User user = (User) request.getSession().getAttribute("userObject");
        List<Project> userProjects = userDAO.getAllUserProjects(user, field, sortType);
        List<Project> sharedToUserProjects = userDAO.getAllSharedToUserProjects(user, field, sortType);
        model.addAttribute("sharedToUserProjects", sharedToUserProjects);
        model.addAttribute("userProjects", userProjects);
        model.addAttribute("userObject", user);
        return "userDashboard";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/create-user", method = RequestMethod.GET)
    public String createUser(@ModelAttribute("user") UserRequest userRequest, HttpServletRequest request, Model model) {
        Object errorMessage = request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage.toString());
            request.getSession().removeAttribute("errorMessage");
        }
        return "userCreation";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createUserInDB(@ModelAttribute("user") UserRequest userRequest, HttpServletRequest request, Model model) {
        if (!isUserRequestValid(userRequest)){
            request.getSession().setAttribute("errorMessage", "Please, fill in all the fields correct");
            return "redirect:/user/create-user";
        }
        User user = userDAO.getUserByEmail(userRequest.getEmail());
        if (user != null) {
            request.getSession().setAttribute("errorMessage", "User with email '" + userRequest.getEmail() + "' already exists");
            return "redirect:/user/create-user";
        }
        userDAO.createUser(userRequest.getFirstName(), userRequest.getLastName(),
                userRequest.getEmail(), userRequest.getPassword(), UserTypes.REGULAR_USER);
        return "redirect:/user/admin-panel/0/s";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable BigInteger id) {
        User user = userDAO.getUserById(id);
        userDAO.deleteUser(user);
    }
}




