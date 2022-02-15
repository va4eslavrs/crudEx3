package com.unsecured.ex2v3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    public LoginController() {
    }

    @GetMapping({"/doLogin"})
    public String toLoginPage() {
        return "loginPage";
    }

    @GetMapping({"/logout"})
    public String logout() {
        return "/doLogin";
    }
}

