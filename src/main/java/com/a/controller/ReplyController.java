package com.a.controller;

import com.a.entity.*;
import com.a.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    @Autowired
    NoticesRepository noticesRepository;
    @PostMapping("/reply/a{id}")
    public String replyA(@RequestParam("articleid") String articleid,
                        @RequestParam("rContent") String rContent,
                         HttpServletResponse response,
                        Model model,
                        HttpServletRequest request,
                        //String[] rContent,
                        HttpSession session) throws IOException {
        Reply reply = new Reply();
        //System.out.println("1:");
        User user = userRepository.findByUsername((String)session.getAttribute("loginuser")).get(0);
        //reply.content = content;
        //String[] s = request.getParameterValues("rContent");
        /*if(s == null) {
            System.out.println("0");
            return "redirect:/main";
        }*/
        if(!user.status){
//            String s = "{\"msg\":\"" + "您已被禁言，无法回复！";
//            s +=   "\"}";
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/json; charset=utf-8");//返回的格式必须设置为application/json
//            response.getWriter().write(s);//写入到返回结果中
////        //完成，执行到这里就会返回数据给前端，前端结果为success，调用success里面的内容
            session.setAttribute("msg","您处于禁言状态，无法操作！");
            return "redirect:/showBlog/"+articleid;

        }
        if(rContent == null || rContent.length() == 0 )
            return "redirect:/main";
        System.out.println(rContent);
        reply.content = rContent;
        reply.userid = user.id;
        reply.username = user.username;
        reply.likes = 0;
        Article article = articleRepository.findArticleById(Integer.parseInt(articleid));
        reply.articleid = article.id;
        reply.create_time = new Date();

        reply.new_like=0;//给reply的这些属性初始化
        reply.new_reply=0;
        reply.new_reply_id=0;
        reply.new_like_id=0;

        userRepository.addExp(reply.userid,2);//yzx
        userRepository.freshLevel(reply.userid);
        Notices temp=new Notices();
        temp.articleid=Integer.parseInt(articleid);
        temp.flag=1;
        temp.f=1;
        temp.fromName=user.username;
        temp.toid=(userRepository.findByUsername(article.author)).get(0).id;
        noticesRepository.save(temp);


        reply = replyRepository.save(reply);
        //System.out.println(reply.content);

        articleRepository.addReply(Integer.parseInt(articleid));//给newreply属性加一
        articleRepository.addNewReply(Integer.parseInt(articleid));
        articleRepository.setNewReplyId(Integer.parseInt(articleid),user.id);

        return "redirect:/showBlog/"+articleid;

    }
    @PostMapping("/reply/r{id}")
    public String replyR(@PathVariable("id") String replyid,
                         @RequestParam("articleid") String articleid,
                        @RequestParam("rContent") String rContent,
                        Model model,
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
        if(!user.status){
            session.setAttribute("msg","您处于禁言状态，无法操作！");
            return "redirect:/showBlog/"+articleid;
        }
        if(rContent == null || rContent.length() == 0 )
            return "redirect:/main";
        System.out.println(rContent);
        reply.content = rContent;
        reply.userid = user.id;
        reply.username = user.username;
        reply.likes = 0;
        Article article = articleRepository.findArticleById(Integer.parseInt(articleid));
        reply.replyid = Integer.parseInt(replyid);
        reply.articleid = article.id;
        reply.create_time = new Date();
        reply = replyRepository.save(reply);
        System.out.println(reply.content);

        userRepository.addExp(reply.userid,2);//yzx
        userRepository.freshLevel(reply.userid);
        Notices temp=new Notices();
        temp.articleid=Integer.parseInt(articleid);
        temp.flag=1;
        temp.f=0;
        temp.fromName=user.username;
        temp.toid=replyRepository.findReplyById(Integer.parseInt(replyid)).userid;
        noticesRepository.save(temp);

        return "redirect:/showBlog/"+articleid;

    }

    @PostMapping("/reply/like/a{id}")
    public String likeA(@PathVariable("id") String articleid,
                        HttpServletRequest request,
                        HttpSession session){

        Article article = articleRepository.findArticleById(Integer.parseInt(articleid));
        User user = userRepository.findByUsername((String)session.getAttribute("loginuser")).get(0);
        Likes likes = likesRepository.findByArticleidAndUseridAndReplyid(Integer.parseInt(articleid),user.id,0);
        //因为reply的赞也会保存article id，故仅根据userId，articleId可能存在结果不唯一的情况，只查询article的赞，它的replyId为0，此时可以查询正确。

        if(likes == null){
            articleRepository.addLikes(Integer.parseInt(articleid));//给文章表中的likes属性++
            articleRepository.addNewLikes(Integer.parseInt(articleid));//给newlike属性++
            articleRepository.setNewLikeId(Integer.parseInt(articleid),(Integer) session.getAttribute("userid"));
            Likes like=new Likes();
            like.userid = user.id;
            like.articleid = Integer.parseInt(articleid);
            like.status = 1;
            likesRepository.save(like);
        }
        else {
            if (likes.status == 0){
                likes.status = 1;
                articleRepository.addLikes(Integer.parseInt(articleid));//给文章表中的likes属性++
                articleRepository.addNewLikes(Integer.parseInt(articleid));//给newlike属性++
                articleRepository.setNewLikeId(Integer.parseInt(articleid),(Integer) session.getAttribute("userid"));
            }
            else {
                likes.status = 0;
                articleRepository.reduceLikes(Integer.parseInt(articleid));//给文章表中的likes属性--
            }
            likesRepository.save(likes);
        }

        int id=articleRepository.findArticleById(Integer.parseInt(articleid)).authorId;
        userRepository.addExp(id,2);//yzx
        userRepository.freshLevel(id);
        Notices temp=new Notices();//yzx
        System.out.println("--------------------------开始");
        temp.articleid=Integer.parseInt(articleid);
        temp.flag=0;
        temp.f=1;
        temp.fromName=user.username;
        temp.toid=(userRepository.findByUsername(article.author)).get(0).id;
        noticesRepository.save(temp);

        return "redirect:/showBlog/"+articleid;
    }


    @PostMapping("/reply/like/r{id}")
    public String likeR(@PathVariable("id") String replyid,
                        @RequestParam("article_id") String articleid,
                        HttpServletRequest request,
                        HttpSession session){
        User user = userRepository.findByUsername((String)session.getAttribute("loginuser")).get(0);
        Likes likes = likesRepository.findByReplyidAndUserid(Integer.parseInt(replyid),user.id);
        if(likes == null){
            Likes like=new Likes();
            like.userid = user.id;
            like.replyid = Integer.parseInt(replyid);
            like.articleid = Integer.parseInt(articleid);
            like.status = 1;
            likesRepository.save(like);
            replyRepository.addLikes(Integer.parseInt(replyid));//同上
            replyRepository.addNewLikes(Integer.parseInt(replyid));
            replyRepository.setNewLikeId(Integer.parseInt(replyid),(Integer) session.getAttribute("userid"));
        }
        else {
            if (likes.status == 1) {
                likes.status = 0;
                replyRepository.reduceLikes(Integer.parseInt(replyid));//同上
            }
            else {
                likes.status = 1;
                replyRepository.addLikes(Integer.parseInt(replyid));//同上
                replyRepository.addNewLikes(Integer.parseInt(replyid));
                replyRepository.setNewLikeId(Integer.parseInt(replyid),(Integer) session.getAttribute("userid"));
            }
            likesRepository.save(likes);
        }
        int id=replyRepository.findReplyById(Integer.parseInt(replyid)).userid;//yzx
        userRepository.addExp(id,2);
        userRepository.freshLevel(id);
        Notices temp=new Notices();//yzx
        temp.articleid=Integer.parseInt(articleid);
        temp.flag=0;
        temp.f=0;
        temp.fromName=user.username;
        temp.toid=replyRepository.findReplyById(Integer.parseInt(replyid)).userid;
        noticesRepository.save(temp);

        return "redirect:/showBlog/"+replyRepository.findReplyById(Integer.parseInt(replyid)).articleid;
    }
}
