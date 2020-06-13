package com.a.controller;
import com.a.entity.Article;
import com.a.repository.ArticleRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Comparator.comparing;

@Controller
public class SearchController {
    @Autowired
    ArticleRepository articleRepository;
    @GetMapping(value = {"/search"})
    public String search(@RequestParam("way") String way,
                         @RequestParam("searchname") String searchname,
                          Model model){
        List<Article> articleList=null;
        List<Article> sumList=new ArrayList<>();
            if(way.compareTo("title")==0) {
                articleList = articleRepository.findArticleByTitleContaining(searchname);
                model.addAttribute("msg", "标题关键词:"+searchname+"。已找到"+articleList.size()+"个相关帖子");
            }
            else if(way.compareTo("author")==0) {
                articleList = articleRepository.findArticleByAuthorContaining(searchname);
                model.addAttribute("msg", "作者关键词:"+searchname+"。已找到"+articleList.size()+"个相关帖子");
            }
//            else if(way.compareTo("levelmore")==0) {
//                articleList = articleRepository.findArticleByLevelGreaterThan(Integer.parseInt(searchname));
//                model.addAttribute("msg", "等级关键词:大于"+searchname+"。已找到"+articleList.size()+"个相关帖子");
//            }
//            else if(way.compareTo("levelless")==0) {
//                articleList = articleRepository.findArticleByLevelLessThan(Integer.parseInt(searchname));
//                model.addAttribute("msg", "等级关键词:小于"+searchname+"。已找到"+articleList.size()+"个相关帖子");
//            }
        int l=articleList.size();
        for(int i=0;i<l;i++){
            if(articleList.get(i).categoryName.compareTo("课程")!=0){
                sumList.add(articleList.get(i));
            }
        }
            model.addAttribute("articles", sumList);
        List<Article> hot = articleRepository.findAll();
        hot.sort(comparing(Article::getlikes).reversed());
        List<Article> sidebar = new ArrayList<>();
        for(int i=0;i<5;i++){
            if(hot.get(i)==null)
                break;
            sidebar.add(hot.get(i));
        }
        model.addAttribute("sidebar",sidebar);
            return "home";
    }
    @GetMapping(value = {"/search/{category}"})
    public String searchfor(
                            @RequestParam("way") String way,
                         @RequestParam("searchname") String searchname,
                            @PathVariable("category") String category,
                         Model model){
        List<Article> articleList=null;
        List<Article> sumList=new ArrayList<>();
        if(way.compareTo("title")==0) {
            articleList = articleRepository.findArticleByTitleContaining(searchname);
            }
        else if(way.compareTo("author")==0) {
            articleList = articleRepository.findArticleByAuthorContaining(searchname);
            }
//            else if(way.compareTo("levelmore")==0) {
//                articleList = articleRepository.findArticleByLevelGreaterThan(Integer.parseInt(searchname));
//                model.addAttribute("msg", "等级关键词:大于"+searchname+"。已找到"+articleList.size()+"个相关帖子");
//            }
//            else if(way.compareTo("levelless")==0) {
//                articleList = articleRepository.findArticleByLevelLessThan(Integer.parseInt(searchname));
//                model.addAttribute("msg", "等级关键词:小于"+searchname+"。已找到"+articleList.size()+"个相关帖子");
//            }
        int l=articleList.size();
        for(int i=0;i<l;i++){
            if(articleList.get(i).category==Integer.parseInt(category.substring(category.length()-1,category.length()))){
                sumList.add(articleList.get(i));
            }
        }
        model.addAttribute("articles", sumList);
        List<Article> hot = articleRepository.findAll();
        hot.sort(comparing(Article::getlikes).reversed());
        List<Article> sidebar = new ArrayList<>();
        for(int i=0;i<5;i++){
            if(hot.get(i)==null)
                break;
            sidebar.add(hot.get(i));
        }
        model.addAttribute("sidebar",sidebar);
        if(way.compareTo("title")==0) {
            model.addAttribute("msg", "标题关键词:"+searchname+"。已找到"+sumList.size()+"个相关帖子");
        }
        else if(way.compareTo("author")==0) {
            model.addAttribute("msg", "作者关键词:"+searchname+"。已找到"+sumList.size()+"个相关帖子");
        }
        return "home";
    }
    @GetMapping(value = {"/mytitle"})
    public String mysearch(HttpSession session){
        List<Article> articleList=null;
        List<Article> sumList=new ArrayList<>();
        String searchname = (String) session.getAttribute("loginuser");
            articleList = articleRepository.findArticleByAuthor(searchname);
            int l=articleList.size();
            for(int i=0;i<l;i++){
                if(articleList.get(i).author.compareTo((String) session.getAttribute("loginuser"))==0)
                    sumList.add(articleList.get(i));
            }
        session.setAttribute("articles", sumList);
        return "userspace";


    }
}
