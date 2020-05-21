package com.a.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    @PostMapping(value = "/index")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model){
        if (!username.isEmpty()&&password.equals("123")){
            return "redirect:/main.html";
        }
        else {
            model.addAttribute("msg", "用户名或密码错误");
            return "redirect:/";
        }
    }
}
