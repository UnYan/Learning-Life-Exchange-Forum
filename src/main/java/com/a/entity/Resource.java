package com.a.entity;

import javax.persistence.*;

@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "fileName", length = 20)
    public String fileName;
    @Column(name = "filePath", length = 500)
    public String filePath;
}
