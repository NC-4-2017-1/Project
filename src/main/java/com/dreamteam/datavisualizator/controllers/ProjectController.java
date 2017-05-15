package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;

//@Controller
public class ProjectController {
    @Autowired
    DataVisualizationProjectDAO projectdao;

    @RequestMapping(path = "/deleteProject", method = RequestMethod.GET)
    public void deleteProject(Project project,
                              Model model) {
        projectdao.deleteProject(project);
    }

}
