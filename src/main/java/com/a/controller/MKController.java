package com.a.controller;

import com.a.entity.Article;
import com.a.entity.Reply;
import com.a.entity.User;
import com.a.repository.ArticleRepository;
import com.a.repository.ReplyRepository;
import com.a.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MKController {
    private static String UPLOADED_FOLDER = "E:\\";
    //TODO!!!!! 在添加到服务器之前要测试地址！！！
    String[] catrgory_name={"管理员帖子","资源共享","校园周边","讨论区","题解","课程推荐","公告","课程"};

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ReplyRepository replyRepository ;
    @Autowired
    UserRepository userRepository ;

    private static String[] s={"(!\\[[^]]+\\]\\([^)]+\\))","(!\\[\\]\\([^)]+\\))","([^!]\\[[^]]+\\]\\([^)]+\\))","([-]{12})","([#]{1,6})"};
    private static String[] target={"[图片]","[图片]","[超链接]","",""};
    public String replacemk(String origin,String cur,String tar){
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(cur);
        Matcher m = p.matcher(origin);

        while (m.find())
        {
            m.appendReplacement(sb, tar);
        }
        m.appendTail(sb);
        return sb.toString();
    }
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
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        article.dateformat=format.format(new Date());
        article.reply_cnt=0;
        for (int i=0;i<s.length;i++){
            content=replacemk(content,s[i],target[i]);
        }
        if (content.length()>30) article.summary=content.substring(0,30);
        else article.summary=content;
        System.out.println(article.summary);
        System.out.println(article.title);
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
    @GetMapping("/showBlog/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Article article = articleRepository.findArticleById(id);
        model.addAttribute("article", article);
        Collection<Reply> replys = replyRepository.findByArticleid(id);
        model.addAttribute("replys",replys);
        model.addAttribute("reply1s",replys);
        Collection<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "editor/article";
    }
    @GetMapping("/main")
    public String home(Model model,HttpSession session) {
        Collection<Article> articles =articleRepository.findAll();
        model.addAttribute("articles", articles);
        User user = userRepository.findByUsername((String)session.getAttribute("loginuser")).get(0);
        model.addAttribute("user",user);
        Collection<Reply> replys = replyRepository.findAll();
        model.addAttribute("replys",replys);
        return "home";
    }
    @GetMapping("/course")
    public String course(Model model) {
        List<Article> coursesList=null;
        coursesList=articleRepository.findArticleByCategoryName("课程");
        int l=coursesList.size();
        while(l>4){
            coursesList.remove(l-1);
            l--;
        }
        model.addAttribute("courses", coursesList);
        Collection<Article> courses =articleRepository.findArticleByCategoryName("课程推荐");
        model.addAttribute("articles", courses);
        Collection<Reply> replys = replyRepository.findAll();
        model.addAttribute("replys",replys);
        return "Course";
    }
}
