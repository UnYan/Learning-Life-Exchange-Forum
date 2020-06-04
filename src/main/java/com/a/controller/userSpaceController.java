package com.a.controller;
import com.a.entity.Article;
import com.a.entity.Reply;
import com.a.repository.ArticleRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

@Controller
public class userSpaceController {
    @Autowired
    ArticleRepository articleRepository;
    @GetMapping(value = {"/userspace"})
    public String login(Model model, HttpSession session){
        String username= (String) session.getAttribute("loginuser");
        List<Article> coursesList=null;
        coursesList=articleRepository.findArticleByAuthor(username);
        model.addAttribute("articles", coursesList);
        return "userspace";
        }

    }
