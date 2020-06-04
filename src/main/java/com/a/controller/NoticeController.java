package com.a.controller;
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
    public String clearNotice(@PathVariable("id") Integer id, Model model){
        articleRepository.clearNewLike(id);
        articleRepository.clearNewReply(id);
        return "redirect:/showBlog/"+id;
    }
}
