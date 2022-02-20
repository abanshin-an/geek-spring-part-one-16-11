package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController  {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login_form")
    public String getLogin() {
        return "login_form";
    }

    @GetMapping("/")
    public String getDefaultLogin() {
        return "login_form";
    }

}
