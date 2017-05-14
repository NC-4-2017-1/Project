package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @Autowired
    UserDAO userdao;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getHello(Model model) {
       int iop = userdao.createUser("firstnam", "lastnam", "email", "passwordl");
       model.addAttribute( "userdao", iop);
        return "index";
    }
}
