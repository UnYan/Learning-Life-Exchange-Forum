package com.a.controller;
import com.a.entity.Reply;
import com.a.repository.ArticleRepository;
import com.a.repository.ReplyRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ReplyRepository replyRepository ;
    @Autowired
    UserRepository userRepository ;

    @RequestMapping("/clear/showBlog/{id}")
    public String clearArticleNotice(@PathVariable("id") Integer id, Model model){
        articleRepository.clearNewLike(id);
        articleRepository.clearNewReply(id);
        if (articleRepository.findArticleById(id)==null){
            return "redirect:/main";
        }
        return "redirect:/showBlog/"+id;
    }
    @RequestMapping("/clear/showBlog/r{id}")
    public String clearReplyNotice(@PathVariable("id") Integer id, Model model){
        Reply reply=replyRepository.findReplyById(id);
        replyRepository.clearNewLike(id);
        replyRepository.clearNewReply(id);
        if (articleRepository.findArticleById(reply.articleid)==null){
            return "redirect:/main";
        }
        return "redirect:/showBlog/"+reply.articleid;
    }
}
