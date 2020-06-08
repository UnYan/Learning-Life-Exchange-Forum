package com.a.controller;

import com.a.entity.Article;
import com.a.repository.ArticleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Controller
public class CategoryController {
    @Autowired
    ArticleRepository articleRepository;
    @GetMapping("/category/{id}")
    public String search(@PathVariable("id") Integer id, Model model, HttpSession session){
        List<Article> list=articleRepository.findArticleByCategory(id);
        model.addAttribute("articles",list);
        // if (id == 5) return "course";
        List<Article> hot = articleRepository.findAll();
        hot.sort(comparing(Article::getlikes).reversed());
        List<Article> sidebar = new ArrayList<>();
        for(int i=0;i<5;i++){
            sidebar.add(hot.get(i));
        }
        model.addAttribute("sidebar",sidebar);
       model.addAttribute("category","category"+String.valueOf(id));
//        model.addAttribute("category","category"+String.valueOf(id));
        return "home1";

    }
}