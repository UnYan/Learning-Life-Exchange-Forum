import com.a.entity.Article;
import com.a.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
//这个不知道是干啥的，问倪某
@Controller
public class CourseSearchController {
    @Autowired
    ArticleRepository articleRepository;
    @GetMapping(value = {"/course"})
    public String search(@RequestParam("searchname") String searchname,
                         @RequestParam("way") String way, Model model){
        List<Article> articleList=null;
        List<Article> courseList=null;
        List<Article> coursesList=null;
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
        coursesList=articleRepository.findArticleByCategoryName("课程");
        int l=coursesList.size();
        while(l>4){
            coursesList.remove(l-1);
            l--;
        }
        l=articleList.size();
        for(int i=0;i<l;i++){
            if(articleList.get(i).categoryName.compareTo("课程推荐")==0){
                courseList.add(articleList.get(i));
            }
        }
        model.addAttribute("courses", coursesList);
        model.addAttribute("articles", courseList);
        model.addAttribute("msg", "标题关键词:"+searchname+"。已找到"+articleList.size()+"个相关帖子");
        return "home";


    }
}