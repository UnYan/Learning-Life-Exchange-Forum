package com.a.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    public int id;
    @Column(name = "username",length = 20, unique = true)
    public String username;
    @Column(name = "password",length = 20)
    private String password;
    @Column(name = "level")
    public  int level;

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



}