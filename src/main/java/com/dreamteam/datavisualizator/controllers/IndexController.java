package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
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
    @Autowired
    DataVisualizationProjectDAO projectdao;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getHello(Model model) {
    /*   model.addAttribute( "userEMAIL", userdao.getUserByFullName("uname usurname").toString());
       model.addAttribute("userID", userdao.getUserById(new BigInteger("1")).toString());*/
        return "index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String tryToLogIn(Model model) {
        return "authorization";
    }

}
