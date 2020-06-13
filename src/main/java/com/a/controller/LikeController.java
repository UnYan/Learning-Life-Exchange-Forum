package com.a.controller;

import com.a.entity.Article;
import com.a.entity.Likes;
import com.a.entity.Notices;
import com.a.entity.User;
import com.a.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//这里和ajax里面的url一致，代表请求的是这个servlet
@RestController
public class LikeController {

    @Autowired
    ReplyRepository replyRepository ;
    @Autowired
    UserRepository userRepository ;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    LikesRepository likesRepository;
    @Autowired
    NoticesRepository noticesRepository;
/*    @RequestMapping("/regist")
    @ResponseBody
    public void regist(){
        System.out.println("1");
    }

    @RequestMapping("test/testAjax1")
    public Map testAjax1(){
        Map<String,String> map = new HashMap<>();
        map.put("aaa","bbb");
        return map;
    }*/
    ////前面请求类型为post函数
    @RequestMapping(value = "/Like/doLike")
    @ResponseBody
    public void doLike(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        //从前端传递的request中取值
        int replyId = Integer.parseInt(request.getParameter("replyId"));
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        //int userId = Integer.parseInt(request.getParameter("userId"));
        System.out.println("1");
        int num = 0;
        if (replyId == 0) {//表示是给article的赞
            User user = userRepository.findByUsername((String) session.getAttribute("loginuser")).get(0);
            Likes likes = likesRepository.findByArticleidAndUseridAndReplyid(articleId, user.id, 0);
            if (likes == null) {
                articleRepository.addLikes(articleId);//给文章表中的likes属性++
                articleRepository.addNewLikes(articleId);//给newlike属性++
                articleRepository.setNewLikeId(articleId, (Integer) session.getAttribute("userid"));
                Likes like = new Likes();
                like.userid = user.id;
                like.articleid = articleId;
                like.status = 1;
                likesRepository.save(like);
                num = articleRepository.findArticleById(articleId).likes;
            } else {
                if (likes.status == 0) {
                    likes.status = 1;
                    articleRepository.addLikes(articleId);//给文章表中的likes属性++
                    articleRepository.addNewLikes(articleId);//给newlike属性++
                    articleRepository.setNewLikeId(articleId, (Integer) session.getAttribute("userid"));
                } else {
                    likes.status = 0;
                    articleRepository.reduceLikes(articleId);//给文章表中的likes属性--
                    articleRepository.reduceNewLikes(articleId);
                }
                likesRepository.save(likes);
                num = articleRepository.findArticleById(articleId).likes;
            }
            int id=articleRepository.findArticleById(articleId).authorId;
            Article article=articleRepository.findArticleById(id);
            userRepository.addExp(id,2);//yzx
            userRepository.freshLevel(id);
            Notices temp=new Notices();//yzx
            temp.articleid=articleId;
            temp.flag=0;
            temp.f=1;
            temp.fromName=user.username;
            temp.toid=(userRepository.findByUsername(article.author)).get(0).id;
            noticesRepository.save(temp);
        } else {
            User user = userRepository.findByUsername((String) session.getAttribute("loginuser")).get(0);
            Likes likes = likesRepository.findByReplyidAndUserid(replyId, user.id);
            if (likes == null) {
                Likes like = new Likes();
                like.userid = user.id;
                like.replyid = replyId;
                like.articleid = articleId;
                like.status = 1;
                likesRepository.save(like);
                replyRepository.addLikes(replyId);//同上
                replyRepository.addNewLikes(replyId);
                replyRepository.setNewLikeId(replyId, (Integer) session.getAttribute("userid"));
                num = replyRepository.findReplyById(replyId).likes;
            } else {
                if (likes.status == 1) {
                    likes.status = 0;
                    replyRepository.reduceLikes(replyId);//同上
                    replyRepository.reduceNewLikes(replyId);
                } else {
                    likes.status = 1;
                    replyRepository.addLikes(replyId);//同上
                    replyRepository.addNewLikes(replyId);
                    replyRepository.setNewLikeId(replyId, (Integer) session.getAttribute("userid"));
                }
                likesRepository.save(likes);
                num = replyRepository.findReplyById(replyId).likes;
            }
            int id=articleRepository.findArticleById(articleId).authorId;
            Article article=articleRepository.findArticleById(id);
            userRepository.addExp(id,2);//yzx
            userRepository.freshLevel(id);
            Notices temp=new Notices();//yzx
            temp.articleid=articleId;
            temp.flag=0;
            temp.f=0;
            temp.fromName=user.username;
            temp.toid=(userRepository.findByUsername(article.author)).get(0).id;
            noticesRepository.save(temp);
        }
//
//        System.out.println("前端传递过来的名字是" + request.getParameter("name"));
//        //构造json字符串传递回前端，\为java转义符号为了转义双引号
//        //注意这里格式要求很严格，不能用单引号，建议使用第三方框架自动生成json字符串
            String s = "{\"likes\":\"" + num;
             s +=   "\"}";
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");//返回的格式必须设置为application/json
            response.getWriter().write(s);//写入到返回结果中
//        //完成，执行到这里就会返回数据给前端，前端结果为success，调用success里面的内容
    }
}
