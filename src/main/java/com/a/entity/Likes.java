package com.a.entity;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;
    @Column(name = "replyid")
    public int replyid;
    @Column(name = "userID")
    public int userid;
    @Column(name = "status")
    public int status;
    @Column(name = "articleid")
    public int articleid;
}
