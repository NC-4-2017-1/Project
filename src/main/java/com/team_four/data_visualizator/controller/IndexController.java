package com.team_four.data_visualizator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getHello(){
        return "index";
    }
}
