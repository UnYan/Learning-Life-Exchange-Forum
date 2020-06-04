package com.a.controller;

import com.a.entity.User;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @PostMapping(value = {"/muted"})
    public String muted(@RequestParam("mute username") String username,
                        Model model, HttpSession session) {
        if (userRepository.findByUsername(username).size() == 1) {
            User tmp = userRepository.findByUsername(username).get(0);
            tmp.setStatus(false);
            userRepository.save(tmp);
            session.setAttribute("status", false);
            model.addAttribute("msg", "禁言成功");
        }
        else {
            model.addAttribute("msg", "该用户不存在");
        }

        return "administrators";
    }

    @PostMapping(value = {"/freed"})
    public String freed(@RequestParam("no mute username") String username,
                        Model model, HttpSession session) {
        if (userRepository.findByUsername(username).size() == 1) {
            User tmp = userRepository.findByUsername(username).get(0);
            tmp.setStatus(true);
            userRepository.save(tmp);
            session.setAttribute("status", true);
            model.addAttribute("mssg", "解禁成功");
        }
        else {
            model.addAttribute("mssg", "该用户不存在");
        }

        return "administrators";
    }

}
