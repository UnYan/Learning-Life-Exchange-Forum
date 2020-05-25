package com.a.controller;
import com.a.repository.ArticleRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
public class SearchController {
    @Autowired
    ArticleRepository articleRepository;
    @GetMapping(value = {"/search"})
    public String search(@RequestParam("searchname") String searchname, Model model){
        if (articleRepository.findArticleByTitleContaining(searchname).size()!=0){
            model.addAttribute("articles", articleRepository.findArticleByTitleContaining(searchname));
            return "home";
        }
        else {
            model.addAttribute("articles", null);
            model.addAttribute("msg", "未找到所查帖子");
            return "redirect:/main";
        }
    }
}