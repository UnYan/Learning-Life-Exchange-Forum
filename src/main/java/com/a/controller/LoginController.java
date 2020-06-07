package com.a.controller;
import com.a.entity.User;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;
    @PostMapping(value = {"/index_login"})
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session){
        if (userRepository.findByUsernameAndPassword(username, password).size() == 1){
            User tmp = userRepository.findByUsername(username).get(0);
            session.setAttribute("loginuser",username);
            session.setAttribute("userid", tmp.id);
            session.setAttribute("level", tmp.level);
            session.setAttribute("status", tmp.status);

            return "redirect:/main";
        }
        else {
            model.addAttribute("msg", "用户名或密码错误");
            return "index";
        }
    }
}