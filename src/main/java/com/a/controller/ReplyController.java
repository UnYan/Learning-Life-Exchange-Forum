package com.a.controller;

import com.a.entity.Article;
import com.a.entity.Likes;
import com.a.entity.Reply;
import com.a.entity.User;
import com.a.repository.ArticleRepository;
import com.a.repository.LikesRepository;
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
    @Autowired
    LikesRepository likesRepository;
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

        reply.new_like=0;//给reply的这些属性初始化
        reply.new_reply=0;
        reply.new_reply_id=0;
        reply.new_like_id=0;

        reply = replyRepository.save(reply);
        System.out.println(reply.content);

        articleRepository.addReply(Integer.parseInt(articleid));//给newreply属性加一
        articleRepository.addNewReply(Integer.parseInt(articleid));
        articleRepository.setNewReplyId(Integer.parseInt(articleid),user.id);

        return "redirect:/showBlog/"+articleid;

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
        return "redirect:/showBlog/"+articleid;

    }

    @PostMapping("/reply/like/a{id}")
    public String likeA(@PathVariable("id") String articleid,
                        HttpServletRequest request,
                        //String[] rContent,
                        HttpSession session){

        Article article = articleRepository.findArticleById(Integer.parseInt(articleid));
        User user = userRepository.findByUsername((String)session.getAttribute("loginuser")).get(0);
        Likes likes = likesRepository.findByArticleidAndUserid(Integer.parseInt(articleid),user.id);

        articleRepository.addLikes(Integer.parseInt(articleid));//给文章表中的likes属性++
        articleRepository.addNewLikes(Integer.parseInt(articleid));//给newlike属性++
        articleRepository.setNewLikeId(Integer.parseInt(articleid),(Integer) session.getAttribute("userid"));

        if(likes == null){
            Likes like=new Likes();
            like.userid = user.id;
            like.articleid = Integer.parseInt(articleid);
            like.status = 1;
            likesRepository.save(like);
        }
        else {
            if (likes.status == 0)
                likes.status = 1;
            else
                likes.status = 0;
            likesRepository.save(likes);
        }
        return "redirect:/showBlog/"+articleid;
    }


    @PostMapping("/reply/like/r{id}")
    public String likeR(@PathVariable("id") String replyid,
                        HttpServletRequest request,
                        //String[] rContent,
                        HttpSession session){
        System.out.println("1");
        User user = userRepository.findByUsername((String)session.getAttribute("loginuser")).get(0);
        Likes likes = likesRepository.findByReplyidAndUserid(Integer.parseInt(replyid),user.id);
        if(likes == null){
            Likes like=new Likes();
            like.userid = user.id;
            like.replyid = Integer.parseInt(replyid);
            like.status = 1;
            likesRepository.save(like);
        }
        else {
            if (likes.status == 1)
                likes.status = 0;
            else
                likes.status = 1;
            likesRepository.save(likes);
        }
        replyRepository.addLikes(Integer.parseInt(replyid));//同上
        replyRepository.addNewLikes(Integer.parseInt(replyid));
        replyRepository.setNewLikeId(Integer.parseInt(replyid),(Integer) session.getAttribute("userid"));
        return "redirect:/showBlog/"+replyRepository.findReplyById(Integer.parseInt(replyid)).articleid;
    }
}
