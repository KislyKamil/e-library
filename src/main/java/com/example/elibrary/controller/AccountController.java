package com.example.elibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {

    @RequestMapping(path = "/Account/")
    public String mainPage(){

        return "userPanel";
    }
}
