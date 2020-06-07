package com.a.entity;

import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {//yzx:登录1经验，发帖10经验，评论2经验，被点赞2经验，每级经验值递增10。10,20,30...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "username",length = 20)
    public String username;
    @Column(name = "password",length = 20)
    private String password;
    @Column(name = "userlevel")
    public  int level;
    @Column(name = "email")
    public String email;
    @Column(name = "status")
    public boolean status;
    @Column(name = "first_use")
    public int first_use;
    public int exp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}