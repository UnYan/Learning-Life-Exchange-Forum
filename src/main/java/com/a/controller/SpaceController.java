package com.a.controller;

import com.a.entity.Notices;
import com.a.entity.User;
import com.a.repository.NoticesRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SpaceController {
    @Autowired
    NoticesRepository noticesRepository;
    @Autowired
    UserRepository userRepository;
    @RequestMapping("/spaceLike")
    public String spaceLike(Model model, HttpSession session){
        User user=userRepository.findByUsername((String) session.getAttribute("loginuser")).get(0);
        List<Notices> list=noticesRepository.findLike(user.id);
        List<Notice> temp=new ArrayList<>();
        for (Notices i:list){
            temp.add(new Notice(i.fromName+"赞了您的一篇"+((i.f==0)?"回复":"文章"),"/showBlog/"+i.articleid));
        }
        model.addAttribute("spaceList",temp);
        return "space";
    }
    @RequestMapping("/spaceReply")
    public String spaceReply(Model model, HttpSession session){
        User user=userRepository.findByUsername((String) session.getAttribute("loginuser")).get(0);
        List<Notices> list=noticesRepository.findReply(user.id);
        List<Notice> temp=new ArrayList<>();
        for (Notices i:list){
            temp.add(new Notice(i.fromName+"回复了您的一篇"+((i.f==0)?"回复":"文章"),"/showBlog/"+i.articleid));
        }
        model.addAttribute("spaceList",temp);
        return "space";
    }
}
