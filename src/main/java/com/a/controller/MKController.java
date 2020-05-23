package com.a.controller;

import com.a.entity.Article;
import com.a.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MKController {
    @Autowired
    ArticleRepository articleRepository;

    @RequestMapping("/article/addArticle")
    public String upload(@RequestParam("title") String title,
                         @RequestParam("author") String author,
                         @RequestParam("content") String content){
        Article article=new Article();
        article.author=author;
        article.title=title;
        article.content=content;
        if (content.)
        articleRepository.save(article);
        return "redirect:/main";
    }

}
