package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;

@Controller
public class IndexController {
    @Autowired
    UserDAO userdao;
    @Autowired
    DataVisualizationProjectDAO projectdao;

    @Autowired
    DataSource dataSource;

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

    @RequestMapping(path = "/herokutest", method = RequestMethod.GET)
    public String testHeroku(Model model) {
        model.addAttribute("sysvar1", System.getenv("SQL_JDBC_URL"));
        model.addAttribute("sysvar2", System.getenv("SQL_LOGIN"));
        model.addAttribute("sysvar3", System.getenv("SQL_PASSWORD"));
        model.addAttribute("datasource", dataSource);
        model.addAttribute("userdao", userdao);
        model.addAttribute("projectdao", projectdao);
        return "herokutest";
    }


}
