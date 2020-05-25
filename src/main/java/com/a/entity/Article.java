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
    public String summary="简介";
    @Column(name = "content", length = 10000)
    public String content;
    @Column(name = "date", length = 100)
    public Date date;
}
