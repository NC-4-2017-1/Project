package com.dreamteam.datavisualizator.controllers;

import com.dreamteam.datavisualizator.dao.UserDAO;
import com.dreamteam.datavisualizator.models.Project;
import com.dreamteam.datavisualizator.models.User;
import com.dreamteam.datavisualizator.models.UserRequest;
import com.dreamteam.datavisualizator.models.UserTypes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

import static com.dreamteam.datavisualizator.common.util.UserRequestValidator.isUserRequestValid;

@Controller
@RequestMapping("/user")
public class UserController {

    public static final Logger LOGGER = Logger.getLogger(UserController.class);

    @Autowired
    private UserDAO userDAO;

    @Secured("ROLE_ADMIN")
    /*@RequestMapping(path = "/admin-panel/{field}/{sortType}", method = RequestMethod.GET)
    public String getAdminDashboard(Model model, @PathVariable String field,@PathVariable String sortType) {*/
    @RequestMapping(path = "/admin-panel", method = RequestMethod.GET)
    public String getAdminDashboard(Model model,
                               @RequestParam(value = "field", required = false, defaultValue = "email") String field,
                               @RequestParam(value = "sortType", required = false, defaultValue = "asc") String sortType,
                               @RequestParam(value = "whereEmail", required = false, defaultValue = "") String whereEmail
    ) {
        if(!"desc".equals(sortType) && !"asc".equals(sortType)) {
            sortType = "asc";
        }
        if (!"first_name".equals(field) && !"last_name".equals(field) &&!"email".equals(field)){
            field = "email";
        }
        if (whereEmail.length() > 30){
            model.addAttribute("error1", "Email can`t be more then 30 characters.");
            return "adminDashboard";
        }
        model.addAttribute("SearchUserEmail",whereEmail);
        model.addAttribute("sortF",field);
        model.addAttribute("sortT",sortType);
        List<User> users = userDAO.getAllUsersList(field, sortType, whereEmail.isEmpty()?null:whereEmail);
        if (users == null) {
            model.addAttribute("error1", "Users wasn't selected.");
        }
        model.addAttribute("users", users);
        return "adminDashboard";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/admin-panel", method = RequestMethod.POST)
   // @ResponseBody
    public String getAdminDashboardWithSearch(@RequestParam(value = "SearchUser", defaultValue = "") String SearchUserEmail,
                                                @RequestParam(value = "search", defaultValue = "") String search,
                                                @RequestParam(value = "cancel", defaultValue = "") String cancel,
                                                @RequestParam(value = "field", required = false, defaultValue = "email") String field,
                                                @RequestParam(value = "sortType", required = false, defaultValue = "asc") String sortType,
                                             Model model) {
        if (SearchUserEmail.length() > 30){
            model.addAttribute("error1", "Email can`t be more then 30 characters.");
            return "adminDashboard";
        }
        if(!cancel.isEmpty()){
            SearchUserEmail = null;
            field = "email";
            sortType = "asc";
        }
        model.addAttribute("sortF", field);
        model.addAttribute("sortT", sortType);
        if(!search.isEmpty()){
            SearchUserEmail = (SearchUserEmail.isEmpty() ? null : SearchUserEmail);
        }
        model.addAttribute("SearchUserEmail",SearchUserEmail);
        model.addAttribute("search",search);
        List<User> users = userDAO.getAllUsersList(field, sortType, SearchUserEmail);
        if (users == null) {
            model.addAttribute("error1", "Users wasn't find.");
        }
        model.addAttribute("users", users);
        return "adminDashboard";
    }

    @Secured("ROLE_REGULAR_USER")
  /*  @RequestMapping(path = "/dashboard-get/{field}/{sortType}/{sortTab}", method = RequestMethod.GET)
    public String getUserDashboard(@PathVariable String field,
                                   @PathVariable String sortType,
                                   @PathVariable String sortTab,
                                   HttpServletRequest request, Model model) {*/
    @RequestMapping(path = "/dashboard-get", method = RequestMethod.GET)
    public String getUserDashboard( @RequestParam(value = "field", required = false, defaultValue = "creation_date") String field,
                                    @RequestParam(value = "sortType", required = false, defaultValue = "desc") String sortType,
                                    @RequestParam(value = "fieldSP", required = false, defaultValue = "creation_date") String fieldSP,
                                    @RequestParam(value = "sortTypeSP", required = false, defaultValue = "desc") String sortTypeSP,
                                    @RequestParam(value = "sortTab", required = false, defaultValue = "1") String sortTab,
                                    @RequestParam(value = "SearchProject", required = false, defaultValue = "") String SearchProject,
                                    @RequestParam(value = "SearchShareProject",required = false,  defaultValue = "") String SearchShareProject,
                                   HttpServletRequest request, Model model) {
        if (!"1".equals(sortTab) && !"2".equals(sortTab)){
            sortTab = "1";
        }
        if ("1".equals(sortTab) && SearchProject.length() > 150){
            model.addAttribute("error1", "Project name can`t be more then 150 characters.");
            return "userDashboard";
        }
        if ("2".equals(sortTab) && SearchShareProject.length() > 150){
            model.addAttribute("error2", "Shared project name can`t be more then 150 characters.");
            return "userDashboard";
        }
        sortType = (!"desc".equals(sortType) && !"asc".equals(sortType))?"desc":sortType;
        sortTypeSP = (!"desc".equals(sortTypeSP) && !"asc".equals(sortTypeSP))?"desc":sortTypeSP;
        if ("1".equals(sortTab) && (!"name".equals(field) && !"creation_date".equals(field))){
            field = "creation_date";
        }
        if ("2".equals(sortTab) && (!"name".equals(fieldSP) && !"creation_date".equals(fieldSP) &&!"author_name".equals(fieldSP))){
            fieldSP = "creation_date";
        }
        model.addAttribute("searchName",SearchProject);
        model.addAttribute("searchShareName",SearchShareProject);
        model.addAttribute("sortF",field);
        model.addAttribute("sortT",sortType);
        model.addAttribute("sortFSP",fieldSP);
        model.addAttribute("sortTSP",sortTypeSP);
        model.addAttribute("sortTab",sortTab);
        User user = (User) request.getSession().getAttribute("userObject");
        List<Project> userProjects = userDAO.getAllUserProjects(user, field, sortType, SearchProject.isEmpty()?null:SearchProject);
        if (userProjects == null) {
            model.addAttribute("error1", "User projects wasn't selected.");
        }
        List<Project> sharedToUserProjects = userDAO.getAllSharedToUserProjects(user, fieldSP, sortTypeSP, SearchShareProject.isEmpty()?null:SearchShareProject);
        if (sharedToUserProjects == null) {
            model.addAttribute("error2", "User shared projects wasn't selected.");
        }
        model.addAttribute("sharedToUserProjects", sharedToUserProjects);
        model.addAttribute("userProjects", userProjects);
        model.addAttribute("userObject", user);
        return "userDashboard";
    }

    @Secured("ROLE_REGULAR_USER")
    @RequestMapping(path = "/dashboard-search", method = RequestMethod.POST)
   // @ResponseBody
    public String getUserDashboardWithSearch(@RequestParam(value = "SearchProject", defaultValue = "") String SearchProject,
                                             @RequestParam(value = "SearchShareProject", defaultValue = "") String SearchShareProject,
                                             @RequestParam(value = "search", defaultValue = "") String search,
                                             @RequestParam(value = "cancel", defaultValue = "") String cancel,
                                             @RequestParam(value = "tab", defaultValue = "1") String tab,
                                             @RequestParam(value = "field", required = false, defaultValue = "creation_date") String field,
                                             @RequestParam(value = "sortType", required = false, defaultValue = "desc") String sortType,
                                             @RequestParam(value = "fieldSP", required = false, defaultValue = "creation_date") String fieldSP,
                                             @RequestParam(value = "sortTypeSP", required = false, defaultValue = "desc") String sortTypeSP,
                                             HttpServletRequest request,
                                             Model model) {

        tab = (!"1".equals(tab) && !"2".equals(tab))?"1":tab;
        if(!cancel.isEmpty() && "1".equals(tab)){
            SearchProject = null;
        }
        if(!cancel.isEmpty() && "2".equals(tab)){
            SearchShareProject = null;
        }
        if(!search.isEmpty()){
            SearchProject = (SearchProject.isEmpty() ? null : SearchProject);
            SearchShareProject = (SearchShareProject.isEmpty() ? null : SearchShareProject);
        }

        model.addAttribute("searchName",SearchProject);
        model.addAttribute("searchShareName",SearchShareProject);
        model.addAttribute("search",search);
        model.addAttribute("sortTab",tab);
        model.addAttribute("sortF",field);
        model.addAttribute("sortT",sortType);
        model.addAttribute("sortFSP",fieldSP);
        model.addAttribute("sortTSP",sortTypeSP);
        User user = (User) request.getSession().getAttribute("userObject");
        List<Project> userProjects = userDAO.getAllUserProjects(user, field, sortType, SearchProject);
        if (userProjects == null) {
            model.addAttribute("error1", "User projects that name contained '" + SearchProject + "' wasn't find.");
        }
        List<Project> sharedToUserProjects = userDAO.getAllSharedToUserProjects(user, fieldSP, sortTypeSP, SearchShareProject);
        if (sharedToUserProjects == null) {
            model.addAttribute("error2", "User shared projects that name contained '" + SearchShareProject + "'  wasn't find.");
        }
        model.addAttribute("sharedToUserProjects", sharedToUserProjects);
        model.addAttribute("userProjects", userProjects);
        model.addAttribute("userObject", user);
        return "userDashboard";
       // return  SearchProject + search + cancel + tab;
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
        return "redirect:/user/admin-panel";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(value = "id") String idUser, RedirectAttributes redir) {
        try {
            Integer id = Integer.parseInt(idUser.trim());
            User user = userDAO.getUserById(BigInteger.valueOf(id));
            Boolean deleteUser = false;
            deleteUser = userDAO.deleteUser(user);
            if (deleteUser){
                redir.addFlashAttribute("deleteMessageTrue", "User " + user.getFullName() + " with email: " +
                        user.getEmail()+ " was delete.");
            } else {
                redir.addFlashAttribute("deleteMessageFalse","User " + user.getFullName() + " with email: " +
                        user.getEmail()+ " was not deleted.");
            }
            return "redirect:/user/admin-panel";
        } catch (NumberFormatException e) {
            LOGGER.error("User delete error - user id '" + idUser + "' is wrong. Can not parsed this id to number.", e);
            redir.addFlashAttribute("deleteMessageFalse","User delete error - user id '" + idUser + "' is wrong.");
            return "redirect:/user/admin-panel";
        } catch (Exception e) {
            LOGGER.error("User delete error - user with id '" + idUser + "' is broken.", e);
            redir.addFlashAttribute("deleteMessageFalse","User delete error - user with id '" + idUser + "' is broken.");
            return "redirect:/user/admin-panel";
        }
    }
}




