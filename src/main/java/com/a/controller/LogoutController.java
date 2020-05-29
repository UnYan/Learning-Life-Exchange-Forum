package com.a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginuser");
        session.removeAttribute("level");
        return "index";
    }
}
