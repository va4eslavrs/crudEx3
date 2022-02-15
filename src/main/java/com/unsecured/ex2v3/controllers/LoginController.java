package com.unsecured.ex2v3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    public LoginController() {
    }

    @GetMapping(value="/doLogin")
    public String toLoginPage() {
        return "loginPage";
    }

    @GetMapping(value="/logout")
    public String logout() {
        return "/doLogin";
    }
}

