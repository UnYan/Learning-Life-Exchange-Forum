package com.a.controller;

import com.a.entity.Article;
import com.a.entity.Resource;
import com.a.repository.ArticleRepository;

import com.a.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Controller
public class CategoryController {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ResourceRepository resourceRepository;

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

    @GetMapping("/resource")
    public String resourceList(Model model) {
        List<Resource> srcList = resourceRepository.findAll();
        model.addAttribute("resources", srcList);
        return "resource";
    }
}