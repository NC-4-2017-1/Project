package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.util.Date;

@Controller
public class IndexController {
    @Autowired
    UserDAO userdao;
    @Autowired
    DataVisualizationProjectDAO projectdao;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getHello(Model model) {
        Project project = new DataVisualizationProject.Builder("project_test2" ,new Date(),
                new BigInteger("2")).description("desctiprion").build();

        projectdao.saveProject(project);
       model.addAttribute( "userdao", (project.getAuthor() + " " + project.getDescription()));
        return "index";
    }
}
