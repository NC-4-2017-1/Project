package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserTypes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @RequestMapping(path = {"/login", "/"}, method = RequestMethod.GET)
    public String tryToLogIn(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userObject");
        if (user!=null) {
            if (user.getUserType().equals(UserTypes.ADMIN)) {
                return "redirect:/user/admin-panel";
            }
            if (user.getUserType().equals(UserTypes.REGULAR_USER)) {
                return "redirect:/user/dashboard";
            }
        }
        return "authorization";
    }



}
