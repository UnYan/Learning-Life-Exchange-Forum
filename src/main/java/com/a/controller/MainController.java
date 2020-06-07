package com.a.controller;

import com.a.entity.Article;
import com.a.entity.Reply;
import com.a.entity.User;
import com.a.repository.ArticleRepository;
import com.a.repository.ReplyRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

@Controller
public class MainController {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ReplyRepository replyRepository ;
    @Autowired
    UserRepository userRepository ;
    @RequestMapping("/main")
    public String home(Model model, HttpSession session) {
        List<Article> articles =articleRepository.findAll();
        List<Article> sumList = new ArrayList<>();
        int l=articles.size();
        for(int i=0;i<l;i++){
            if(articles.get(i).categoryName.compareTo("课程")!=0){
                sumList.add(articles.get(i));
            }
        }
        model.addAttribute("articles", sumList);
        User user = userRepository.findByUsername((String)session.getAttribute("loginuser")).get(0);

        List<Article> nArticles = articleRepository.findArticleByNotice(user.username);
        List<Notice > noticeBuff=new ArrayList<>();
        for (Article i:nArticles){
            if (i.new_like>0){
                String s=(i.new_like>1)?"等"+i.new_like+"人":"";
                noticeBuff.add(new Notice(userRepository.findUserById(i.new_like_id).username+s+"赞了你的一篇文章","/clear/showBlog/"+i.id));
            }
            if (i.new_reply>0){
                String s=(i.new_reply>1)?"等"+i.new_reply+"人":"";
                noticeBuff.add(new Notice(userRepository.findUserById(i.new_reply_id).username+s+"回复了你的一篇文章","/clear/showBlog/"+i.id));
            }
        }
//
        List<Reply> nReply = replyRepository.findReplyByNotice(user.id);
        for (Reply i:nReply){
            if (i.new_like>0){
                String s=(i.new_like>1)?"等"+i.new_like+"人":"";
                noticeBuff.add(new Notice(userRepository.findUserById(i.new_like_id).username+s+"赞了你的一条回复","/clear/showBlog/r"+i.id));
            }
            if (i.new_reply>0){
                String s=(i.new_reply>1)?"等"+i.new_reply+"人":"";
                noticeBuff.add(new Notice(userRepository.findUserById(i.new_reply_id).username+s+"回复了你的一条回复","/clear/showBlog/r"+i.id));
            }
        }
//        model.addAttribute("notices",noticeBuff);
        session.setAttribute("notices",noticeBuff);
        List<Article> hot = articleRepository.findAll();
        hot.sort(comparing(Article::getlikes).reversed());
        List<Article> sidebar = new ArrayList<>();
        for(int i=0;i<5;i++){
            sidebar.add(hot.get(i));
        }
        model.addAttribute("sidebar",sidebar);
        model.addAttribute("user",user);
        Collection<Reply> replys = replyRepository.findAll();
        model.addAttribute("replys",replys);
        return "home";
    }
}

class Notice{
    public String msg;
    public String href;
    public Notice(String msg,String href){
        this.msg=msg;
        this.href=href;
    }
}