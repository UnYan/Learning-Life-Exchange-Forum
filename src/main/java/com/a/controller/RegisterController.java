package com.a.controller;


import com.a.entity.User;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller

public class RegisterController {
    UserRepository userRepository;
    @PostMapping(value = "/register")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password1") String password1,
                        @RequestParam("password2") String password2,
                        Map<String,Object>map)
    {
        if (userRepository.findByUsername(username)!=null){
            map.put("msg", "用户名已存在");
            return "redirect:/register";
        }
        if (password1.compareTo(password2)!=0){
            map.put("msg", "密码不一致");
            return "redirect:/hello";

        }
        User tmp =new User();
        tmp.id=1;
        tmp.username=username;
        tmp.setPassword(password1);
        userRepository.save(tmp);
        return "redirect:/main.html";
    }
}
