package com.a.controller;

import com.a.entity.Article;
import com.a.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    ArticleRepository articleRepository;
    @GetMapping("/category/{id}")
    public String search(@PathVariable("id") Integer id, Model model){
        List<Article> list=articleRepository.findArticleByCategory(id);
        model.addAttribute("articles",list);
        if(id==5)
            return "course";
        return "home";

    }
}