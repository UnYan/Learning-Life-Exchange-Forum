package com.a.controller;


import com.a.entity.User;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller

public class RegisterController {
    @Autowired
    UserRepository userRepository;
    int s;
    @PostMapping(value = "/register")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password1") String password1,
                        @RequestParam("password2") String password2,
                        Map<String,Object>map)
    {
        s=userRepository.findByUsername(username).size();
        if (s!=0){
            map.put("msg", "用户名已存在");
            return "redirect:/register";
        }
        if (password1.compareTo(password2)!=0){
            map.put("msg", "密码不一致");
            return "/static/register.html";

        }
        User tmp =new User();
        tmp.id=1;
        tmp.username=username;
        tmp.level=1;
        tmp.setPassword(password1);
        System.out.println("----------------------------");
        userRepository.save(tmp);
        return "redirect:/";
    }
}
