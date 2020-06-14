package com.a.controller;

import com.a.entity.Article;
import com.a.entity.Reply;
import com.a.entity.User;
import com.a.repository.ArticleRepository;
import com.a.repository.ReplyRepository;
import com.a.repository.UserRepository;
import org.apache.commons.io.FileUtils;
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

import static java.util.Comparator.comparing;

@Controller
public class MKController {
    private static String UPLOADED_FOLDER = "uploadImg/";
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
                         @RequestParam("level") int level,
                         Model model,
                         HttpSession session){
        Article article=new Article();

        article.author=(String) session.getAttribute("loginuser");
        User tmp =userRepository.findByUsername(article.author).get(0);
        if(!tmp.status){
            session.setAttribute("msg","您处于禁言状态，无法操作！");
            return "redirect:/main";
        }
        if(tmp.level<level&&tmp.level!=0) {
            session.setAttribute("msg","不能设置比自己等级高的权限");
            return "redirect:/mk";
        }
        if(content.length()==0) {
            session.setAttribute("msg","帖子内容不能为空");
            return "redirect:/mk";
        }
        article.title=title;
        article.level=level;
        article.content=content;
        article.likes=0;
        article.category=category;
        article.new_reply=0;
        article.new_like=0;
        article.categoryName=catrgory_name[category];
        article.authorId=userRepository.findByUsername(article.author).get(0).id;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        article.dateformat=format.format(new Date());
        article.reply_cnt=0;
        if (category==6) article.pinned=1;
        else article.pinned=0;
        userRepository.addExp(article.authorId,10);
        userRepository.freshLevel(article.authorId);

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
        //保存
        try {
            File imageFolder= new File("/home/yzx/springboot/uploadImg");
            File targetFile = new File(imageFolder,file.getOriginalFilename());
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();

            }
            System.out.println(targetFile.getAbsolutePath());
            FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
//            BufferedImage img = ImageUtil.change2jpg(targetFile);
//            ImageIO.write(img, "jpg", targetFile);
            /*            file.transferTo(targetFile);*/
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(realPath + file.getOriginalFilename());
//            Files.write(path, bytes);
            resultMap.put("success", 1);
            resultMap.put("message", "上传成功！");
            resultMap.put("url","/UploadImages/"+file.getOriginalFilename());
        } catch (Exception e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        }
        System.out.println(resultMap.get("success"));
        return resultMap;
    }
    @GetMapping("/showBlog/{id}")
    public String show(@PathVariable("id") Integer id, Model model,HttpSession session) {
        Article article = articleRepository.findArticleById(id);


        if(session.getAttribute("loginuser") == null){
            model.addAttribute("msg", "请先注册");
            return "index";
        }
        else if (article==null){
            return "redirect:/main";
        }
        else if((Integer)session.getAttribute("level") < article.level&&(Integer)session.getAttribute("level")!=0){
            model.addAttribute("msg", "您的权限不足，请多多水群");
            return "home";
        }

        String name = article.author;
        List<User> uList = userRepository.findByUsername(name);
        session.setAttribute("otherusrheadImg", uList.get(0).headImgName);

        model.addAttribute("article", article);
        Collection<Reply> replys = replyRepository.findByArticleid(id);
        model.addAttribute("replys",replys);
        model.addAttribute("reply1s",replys);
        Collection<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        List<Article> hot = articleRepository.findAll();
        hot.sort(comparing(Article::getlikes).reversed());
        List<Article> sidebar = new ArrayList<>();
        for(int i=0;i<5;i++){
            if(hot.size()<=i)
                break;
            sidebar.add(hot.get(i));
        }
        model.addAttribute("sidebar",sidebar);
        return "editor/article";
    }
    @GetMapping("/Course")
    public String course(Model model) {
        List<Article> coursesList=null;
        coursesList=articleRepository.findArticleByCategoryName("课程");
        model.addAttribute("courses", coursesList);
        Collection<Article> courses =articleRepository.findArticleByCategoryName("课程推荐");
        model.addAttribute("articles", courses);
        Collection<Reply> replys = replyRepository.findAll();
        model.addAttribute("replys",replys);
        List<Article> hot = articleRepository.findAll();
        hot.sort(comparing(Article::getlikes).reversed());
        List<Article> sidebar = new ArrayList<>();
        for(int i=0;i<5;i++){
            if(hot.size()<=i)
                break;
            sidebar.add(hot.get(i));
        }
        model.addAttribute("sidebar",sidebar);
        return "Course";
    }
}
