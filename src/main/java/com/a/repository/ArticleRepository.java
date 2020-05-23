package com.a.repository;

import com.a.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findArticleByAuthor(String author);
    List<Article> findArticleByTitle(String title);
    List<Article> findArticleById(int id);
}
