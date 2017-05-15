package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.DataVisualizationProjectDAO;
import com.dreamteam.datavisualizator.dao.impl.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectController extends UserDAOImpl  {
    @Autowired
    DataVisualizationProjectDAO projectdao;

}
