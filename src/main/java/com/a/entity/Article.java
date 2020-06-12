package com.a.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "title", length = 100)
    public String title;
    @Column(name = "author", length = 100)
    public String author;
    public int authorId;
    @Column(name = "summary", length = 200)
    public String summary;
    @Column(name = "level")
    public Integer level;
    @Column(name = "content", length = 10000)
    public String content;
    @Column(name = "likes", length = 100)
    public Integer likes;

    public Integer getlikes() {
        return likes;
    }

    @Column(name = "category", length = 100)
    public Integer category;
    @Column(name = "categoryName", length = 100)
    public String categoryName;
    @Column(name = "reply_cnt")
    public Integer reply_cnt;
    public String dateformat;
    public Integer new_like;//这个属性是代表新增的赞的数量，
    public Integer new_like_id;/*这个属性是最后一个点赞的用户id，我设想的通知栏是“xxx等几人赞了你的文章”，然后用户点击之后跳转到对应文章页面
                            /*注意：用户点击该通知后，需要将两个newlike属性清零，代表此时没有新增点赞。清零涉及到update操作，和查找还不太一样，可以参考
                            articleRepository当中的更新操作，然后目前这一版涉及到newlike和newreply的操作都在replyController中，有注释
                            但目前点赞会出bug，原因在replycontroller当中，严某写的代码我没敢改，怕出现更多bug。。。。
                            newreply的操作和点赞类似。
                            现在需要做的就是从前端读取这些带new的属性然后显示在通知栏当中，并且点击后对new清零。加油，yzx与你同在！
                            */
    public Integer new_reply;
    public Integer new_reply_id;
    public int pinned;
//    @Column(name = "level")
//    public int level;
}
