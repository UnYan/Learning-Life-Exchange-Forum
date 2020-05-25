package com.a.controller;

import com.a.entity.Article;
import com.a.repository.ArticleRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MKController {
    private static String UPLOADED_FOLDER = "E://";
    //TODO!!!!! 在添加到服务器之前要测试地址！！！

    @Autowired
    ArticleRepository articleRepository;
    String[] catrgory_name={"管理员帖子","帖子","校园周边","讨论区","题解"};
    @RequestMapping("/article/addArticle")
    public String upload(@RequestParam("title") String title,
                         @RequestParam("content") String content,
                         @RequestParam("category") int category,
                         HttpSession session){
        Article article=new Article();
        article.author=(String) session.getAttribute("loginuser");
        article.title=title;
        article.content=content;
        article.likes=0;
        article.category=category;
        article.categoryName=catrgory_name[category];
        articleRepository.save(article);
        return "redirect:/main";
    }
    @RequestMapping(value="/uploadimg")
    public @ResponseBody Map<String,Object> demo(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        System.out.println(request.getContextPath());
        String realPath = UPLOADED_FOLDER;
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            resultMap.put("success", 1);
            resultMap.put("message", "上传成功！");
            resultMap.put("url",UPLOADED_FOLDER+fileName);
        } catch (Exception e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        }
        System.out.println(resultMap.get("success"));
        return resultMap;
    }
    @GetMapping("/show")
    public String show(Model model) {
        Article article = articleRepository.findArticleById(13).get(0);
        model.addAttribute("article", article);
        return "editor/article";
    }
    @GetMapping("/main")
    public String home(Model model) {
        Collection<Article> articles =articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "home";
    }
}
