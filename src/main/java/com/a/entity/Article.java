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
    @Column(name = "summary", length = 200)
    public String summary;
    @Column(name = "content", length = 10000)
    public String content;
    @Column(name = "likes", length = 100)
    public Integer likes;
    @Column(name = "category", length = 100)
    public Integer category;
    @Column(name = "categoryName", length = 100)
    public String categoryName;
    @Column(name = "reply_cnt")
    public Integer reply_cnt;
    public String dateformat;
//    @Column(name = "level")
//    public int level;
}
