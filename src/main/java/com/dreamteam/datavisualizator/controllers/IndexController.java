package com.dreamteam.datavisualizator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(path = {"/login", "/"}, method = RequestMethod.GET)
    public String tryToLogIn(Model model) {
        return "authorization";
    }



}
