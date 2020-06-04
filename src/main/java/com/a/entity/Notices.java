package com.a.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public int toid;
    public String fromName;
    public int flag;//0代表点赞，1代表回复
    public int f;//0回复，1文章
    public int articleid;
}
