package com.a.controller;


import com.a.entity.User;
import com.a.repository.UserRepository;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller

public class UpdatePasswordController {
    @Autowired
    UserRepository userRepository;
    int s;
    @PostMapping(value = "/updatepassword")
    public String updatepassword(@RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("newpassword") String password1,
                                 @RequestParam("confirmpassword") String password2,
                                 Model model)
    {
        s=userRepository.findByUsernameAndEmail(username,email).size();
        if (s==0){
            model.addAttribute("msg", "用户名或邮箱不存在");
            return "/retrivepassword";
        }
        if (password1.compareTo(password2)!=0){
            model.addAttribute("msg", "密码不一致");
            return "/retrivepassword";
        }
        User tmp =userRepository.findByUsernameAndEmail(username,email).get(0);
        tmp.setPassword(password1);
        userRepository.save(tmp);
        return "/index";
    }
}