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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            return "home";


    }
}
