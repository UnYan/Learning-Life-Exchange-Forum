package com.a.repository;

import com.a.entity.Article;
import com.a.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Integer> {
    Likes findByArticleidAndUseridAndReplyid(int aid,int uid,int rid);
    Likes findByReplyidAndUserid(int rid,int uid);
}
