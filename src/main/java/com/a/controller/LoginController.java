package com.a.controller;
import com.a.entity.Article;
import com.a.entity.User;
import com.a.repository.ArticleRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;
    @PostMapping(value = {"/index_login"})
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session){
        if(username == null){
            model.addAttribute("msg", "请填写账号");
            return "register";
        }
        if(password == null ){
            model.addAttribute("msg", "请填写密码");
            return "register";
        }
        if (userRepository.findByUsernameAndPassword(username, password).size() == 1){
            User tmp = userRepository.findByUsername(username).get(0);
            if(tmp.first_use == 0){
                session.setAttribute("loginuser",username);
                session.setAttribute("userid", tmp.id);
                session.setAttribute("level", tmp.level);
                session.setAttribute("status", tmp.status);
                session.setAttribute("fileName", tmp.headImgName);
                userRepository.addExp(tmp.id,1);
                userRepository.freshLevel(tmp.id);
                return "firstUse";
            }
            session.setAttribute("loginuser",username);
            session.setAttribute("userid", tmp.id);
            session.setAttribute("level", tmp.level);
            session.setAttribute("status", tmp.status);
            session.setAttribute("fileName", tmp.headImgName);
//            model.addAttribute("category","main");
            userRepository.addExp(tmp.id,1);
            userRepository.freshLevel(tmp.id);
            return "redirect:/main";
        }
        else {
            model.addAttribute("msg", "用户名或密码错误");
            return "index";
        }
    }
    @PostMapping(value = {"/first_login"})
    public String first_login(Model model, HttpSession session){
        User tmp = userRepository.findByUsername((String) session.getAttribute("loginuser")).get(0);
        tmp.first_use = 1;
        userRepository.save(tmp);
        return "redirect:/main";
    }
    @GetMapping(value = {"/tourist_login"})
    public String tourist_login(Model model, HttpSession session){
            session.setAttribute("loginuser",null);
            session.setAttribute("userid", null);
            session.setAttribute("level", -1);
            session.setAttribute("status", 1);
            model.addAttribute("msg","欢迎游客！");
        return "redirect:/main";
    }
}