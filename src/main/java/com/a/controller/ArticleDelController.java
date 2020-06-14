package com.a.controller;

import com.a.entity.Article;
import com.a.entity.Reply;
import com.a.repository.ArticleRepository;
import com.a.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class ArticleDelController {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ReplyRepository replyRepository;

    @PostMapping(value = {"/delAtc/{id}"})
    public String deleteArticle(@PathVariable("id") Integer id, Model model) {
        List<Reply> relist = replyRepository.findByArticleid(id);
        replyRepository.deleteAll(relist);
        Article article = articleRepository.findArticleById(id);
        articleRepository.delete(article);
        model.addAttribute("res", "删帖成功") ;
        return "editor/article";
    }

    @PostMapping(value = {"/delReply/{id}"})
    public String deleteReply(@PathVariable("id") Integer id, Model model) {
        Reply reply = replyRepository.findReplyById(id);
        if (reply != null) {
            articleRepository.reduceReply(reply.articleid);
            replyRepository.delete(reply);
            model.addAttribute("res", "控评成功");
        }
        else model.addAttribute("res", "控评失败");
        return "editor/article";
    }
}
