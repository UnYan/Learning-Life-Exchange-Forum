package com.a.repository;

import com.a.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findArticleByAuthor(String author);
    List<Article> findArticleByDate(Date date);
//    List<Article> findArticleByLevelGreaterThan(int i);
//    List<Article> findArticleByLevelLessThan(int i);
    List<Article> findArticleByAuthorContaining(String author);
    List<Article> findArticleByTitleContaining(String title);
    List<Article> findArticleByTitle(String title);
    List<Article> findArticleById(int id);
}
