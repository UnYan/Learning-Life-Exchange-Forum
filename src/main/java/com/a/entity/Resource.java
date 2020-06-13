package com.a.entity;

import javax.persistence.*;

@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "author", length = 20)
    public String author;
    @Column(name = "title", length = 50)
    public String title;
    @Column(name = "fileName", length = 20)
    public String fileName;
    @Column(name = "filePath", length = 500)
    public String filePath;
    @Column(name = "type", length = 20)
    public String type;
}
