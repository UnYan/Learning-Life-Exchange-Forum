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
        if (temp.size()==0) temp.add(new Notice("暂无点赞",null));
        model.addAttribute("spaceList",temp);
        model.addAttribute("title","点赞我的");
        return "space";
    }
    @RequestMapping("/otherspaceReply")
    public String otherspaceReply(Model model, HttpSession session){
        User user=userRepository.findByUsername((String) session.getAttribute("otherloginuser")).get(0);
        List<Notices> list=noticesRepository.findReply(user.id);
        List<Notice> temp=new ArrayList<>();
        for (Notices i:list){
            temp.add(new Notice(i.fromName+"回复了您的一篇"+((i.f==0)?"回复":"文章"),"/showBlog/"+i.articleid));
        }
        if (temp.size()==0) temp.add(new Notice("暂无点赞",null));
        model.addAttribute("spaceList",temp);
        model.addAttribute("title","回复我的");
        return "otheruserspace";
    }
    @RequestMapping("/otherspaceLike")
    public String otherspaceLike(Model model, HttpSession session){
        User user=userRepository.findByUsername((String) session.getAttribute("otherloginuser")).get(0);
        List<Notices> list=noticesRepository.findLike(user.id);
        List<Notice> temp=new ArrayList<>();
        for (Notices i:list){
            temp.add(new Notice(i.fromName+"赞了您的一篇"+((i.f==0)?"回复":"文章"),"/showBlog/"+i.articleid));
        }
        if (temp.size()==0) temp.add(new Notice("暂无点赞",null));
        model.addAttribute("spaceList",temp);
        model.addAttribute("title","点赞我的");
        return "otheruserspace";
    }
    @RequestMapping("/spaceReply")
    public String spaceReply(Model model, HttpSession session){
        User user=userRepository.findByUsername((String) session.getAttribute("loginuser")).get(0);
        List<Notices> list=noticesRepository.findReply(user.id);
        List<Notice> temp=new ArrayList<>();
        for (Notices i:list){
            temp.add(new Notice(i.fromName+"回复了您的一篇"+((i.f==0)?"回复":"文章"),"/showBlog/"+i.articleid));
        }
        if (temp.size()==0) temp.add(new Notice("暂无回复",null));
        model.addAttribute("spaceList",temp);
        model.addAttribute("title","回复我的");
        return "space";
    }
}
