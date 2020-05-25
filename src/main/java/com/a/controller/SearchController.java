package com.a.controller;
import com.a.entity.Article;
import com.a.repository.ArticleRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    ArticleRepository articleRepository;
    @GetMapping(value = {"/search"})
    public String search(@RequestParam("searchname") String searchname, Model model){
        List<Article> articleList =articleRepository.findArticleByTitleContaining(searchname);

            model.addAttribute("articles", articleList);
            model.addAttribute("msg", "关键词:"+searchname+"。已找到"+articleList.size()+"个相关帖子");
            return "home";


    }
}