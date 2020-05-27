package com.a.controller;

import com.a.entity.Article;
import com.a.entity.Reply;
import com.a.entity.User;
import com.a.repository.ArticleRepository;
import com.a.repository.ReplyRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ReplyController {

    @Autowired
    ReplyRepository replyRepository ;
    @Autowired
    UserRepository userRepository ;
    @Autowired
    ArticleRepository articleRepository;
    @PostMapping("/reply/a{id}")
    public String replyA(@RequestParam("articleid") String articleid,
                        @RequestParam("rContent") String rContent,
                        HttpServletRequest request,
                        //String[] rContent,
                        HttpSession session){
        Reply reply = new Reply();
        //System.out.println("1:");
        User user = userRepository.findByUsername((String)session.getAttribute("loginuser")).get(0);
        //reply.content = content;
        //String[] s = request.getParameterValues("rContent");
        /*if(s == null) {
            System.out.println("0");
            return "redirect:/main";
        }*/
        if(rContent == null || rContent.length() == 0 )
            return "redirect:/main";
        System.out.println(rContent);
        reply.content = rContent;
        reply.userid = user.id;
        reply.likes = 0;
        Article article = articleRepository.findArticleById(Integer.parseInt(articleid));
        reply.articleid = article.id;
        reply.create_time = new Date();
        reply = replyRepository.save(reply);
        System.out.println(reply.content);
        return "redirect:/main";

    }
    @PostMapping("/reply/r{id}")
    public String replyR(@PathVariable("id") String replyid,
                         @RequestParam("articleid") String articleid,
                        @RequestParam("rContent") String rContent,
                        HttpServletRequest request,
                        //String[] rContent,
                        HttpSession session){
        Reply reply = new Reply();
        //System.out.println("1:");
        User user = userRepository.findByUsername((String)session.getAttribute("loginuser")).get(0);
        //reply.content = content;
        //String[] s = request.getParameterValues("rContent");
        /*if(s == null) {
            System.out.println("0");
            return "redirect:/main";
        }*/
        if(rContent == null || rContent.length() == 0 )
            return "redirect:/main";
        System.out.println(rContent);
        reply.content = rContent;
        reply.userid = user.id;
        reply.likes = 0;
        Article article = articleRepository.findArticleById(Integer.parseInt(articleid));
        reply.replyid = Integer.parseInt(replyid);
        reply.articleid = article.id;
        reply.create_time = new Date();
        reply = replyRepository.save(reply);
        System.out.println(reply.content);
        return "redirect:/main";

    }


}
