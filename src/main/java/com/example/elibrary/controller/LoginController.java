package com.example.elibrary.controller;

import com.example.elibrary.entity.User;
import com.example.elibrary.model.LoginForm;
import com.example.elibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
public class LoginController{

    final UserService userService;

    public LoginController(UserService userService){

        this.userService = userService;
    }


    @RequestMapping(path = "/login")
    @Autowired
    public ModelAndView login() {

        return new ModelAndView("login", "login", new LoginForm());

    }


    @RequestMapping(path = "/loginUser", method = RequestMethod.POST)
    @Scope("session")
    public String loginUser(@ModelAttribute("login") LoginForm user, HttpSession session, ModelMap model, HttpServletRequest request) {

        final String role;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        request.getSession().setAttribute("name", "admin");

        try {
            if (user.getPassword().isEmpty()) {
                return "error";
            }

        } catch (Exception e) {

        }
        ArrayList<User> logged = new ArrayList<>();
        logged.addAll(userService.getUserByName(user.getLogin()));

        



        if (encoder.matches(user.getPassword(), logged.get(0).getPassword())) {

            model.addAttribute("loginName", user.getLogin());
            model.addAttribute("id", logged.get(0).getUserId());

            if (user.getLogin().equals("Admin")) {
                role = "Admin";
            } else {
                role = "user";
            }
            System.out.println("Logged as:  " +  role);
        }else{
            System.out.println("WRONG PASSWORD!!!!!");
        }
        //setUser(user.getLogin(), role);
       // model.addAttribute("role",role);
       // session.setAttribute("who", user.getLogin());


        return "Success";

    }

    /*public void setUser(String name, String role) {

        //this.user = new User (name, role);
        this.user = new User();
        user.setUserName(name);
        user.setUserRole(role);

    }


     */

}
