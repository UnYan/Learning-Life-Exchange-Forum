package com.a.controller;
import com.a.entity.Article;
import com.a.entity.Reply;
import com.a.entity.User;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class userSpaceController {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping(value = {"/userspace"})
    public String login(Model model, HttpSession session){
        String username= (String) session.getAttribute("loginuser");
        User user=userRepository.findByUsername(username).get(0);
        session.setAttribute("level",user.level);
        session.setAttribute("restExp",user.level*10-user.exp);
        session.setAttribute("fileName", user.headImgName);
        List<Article> coursesList=null;
        coursesList=articleRepository.findArticleByAuthor(username);
        model.addAttribute("articles", coursesList);
        return "userspace";
        }
    @GetMapping("/otheruserspace/{author}")
    public String show(@PathVariable("author") String otherusername, Model model,HttpSession session) {
        User user=userRepository.findByUsername(otherusername).get(0);
        session.setAttribute("otherlevel",user.level);
        session.setAttribute("otherrestExp",user.level*10-user.exp);
        session.setAttribute("otherloginuser",otherusername);
        List<Article> coursesList=null;
        coursesList=articleRepository.findArticleByAuthor(otherusername);
        model.addAttribute("otherarticles", coursesList);
        return "otheruserspace";
    }
//    @GetMapping("/otheruserspace/search/{otherloginuser}")
//    public String showother(@RequestParam("searchname") String searchname,@PathVariable("otherloginuser") String otherusername,
//                         Model model,HttpSession session) {
//        User user=userRepository.findByUsername(otherusername).get(0);
//        session.setAttribute("level",user.level);
//        session.setAttribute("restExp",user.level*10-user.exp);
//        List<Article> coursesList=null;
//        List<Article> articleList=new ArrayList<>();
//        coursesList=articleRepository.findArticleByTitleContaining(searchname);
//        int l=coursesList.size();
//        for(int i=0;i<l;i++){
//            if(coursesList.get(i).author.compareTo(otherusername)==0)
//                articleList.add(coursesList.get(i));
//        }
//        model.addAttribute("otherarticles", articleList);
//        model.addAttribute("msg", "标题关键词:"+searchname+"。已找到"+articleList.size()+"个相关帖子");
//        return "otheruserspace";
//    }
@GetMapping("/otheruserspace/search")
public String showother(@RequestParam("searchname") String searchname,
                        Model model,HttpSession session) {
        String otherusername = (String) session.getAttribute("otherloginuser");
    User user=userRepository.findByUsername(otherusername).get(0);
    session.setAttribute("level",user.level);
    session.setAttribute("restExp",user.level*10-user.exp);
    List<Article> coursesList=null;
    List<Article> articleList=new ArrayList<>();
    coursesList=articleRepository.findArticleByTitleContaining(searchname);
    int l=coursesList.size();
    for(int i=0;i<l;i++){
        if(coursesList.get(i).author.compareTo(otherusername)==0)
            articleList.add(coursesList.get(i));
    }
    model.addAttribute("otherarticles", articleList);
    model.addAttribute("msg", "标题关键词:"+searchname+"。已找到"+articleList.size()+"个相关帖子");
    return "otheruserspace";
}
    @GetMapping("/userspace/search")
    public String showmy(@RequestParam("searchname") String searchname,
                                 Model model,HttpSession session) {
        String username= (String) session.getAttribute("loginuser");
        User user=userRepository.findByUsername(username).get(0);
        session.setAttribute("level",user.level);
        session.setAttribute("restExp",user.level*10-user.exp);
        List<Article> coursesList=null;
        List<Article> articleList=new ArrayList<>();
        coursesList=articleRepository.findArticleByTitleContaining(searchname);
        int l=coursesList.size();
        for(int i=0;i<l;i++){
            if(coursesList.get(i).author.compareTo(username)==0)
                articleList.add(coursesList.get(i));
        }
        model.addAttribute("articles", articleList);
        model.addAttribute("msg", "标题关键词:"+searchname+"。已找到"+articleList.size()+"个相关帖子");

        return "userspace";
    }

    }
