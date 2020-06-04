package com.a.repository;

import com.a.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    Article findArticleById(int  id);
    List<Article> findArticleByAuthor(String author);
//    List<Article> findArticleByLevelGreaterThan(int i);
//    List<Article> findArticleByLevelLessThan(int i);
    List<Article> findArticleByAuthorContaining(String author);
    List<Article> findArticleByTitleContaining(String title);
    List<Article> findArticleByCategoryName(String category);
    List<Article> findArticleByCategory(int category);
    List<Article> findAll();
    @Query(value="select * from article where author=?1 and (new_like>0 or new_reply>0) ", nativeQuery=true)
    List<Article> findArticleByNotice(String name);
    @Transactional
    @Modifying
    @Query(value="update article set new_like=0,new_like_id=null where id=?1", nativeQuery=true)
    void clearNewLike(Integer id);
    @Transactional
    @Modifying
    @Query(value="update article set new_reply=0,new_reply_id=null where id=?1", nativeQuery=true)
    void clearNewReply(Integer id);
    @Transactional
    @Modifying
    @Query(value="update article set reply_cnt=reply_cnt+1 where id=?1", nativeQuery=true)
    void addReply(Integer id);
    @Transactional
    @Modifying
    @Query(value="update article set likes=likes+1 where id=?1", nativeQuery=true)
    void addLikes(Integer id);
    @Transactional
    @Modifying
    @Query(value="update article set likes=likes-1 where id=?1", nativeQuery=true)
    void reduceLikes(Integer id);
    @Transactional
    @Modifying
    @Query(value="update article set new_like=new_like+1 where id=?1", nativeQuery=true)
    void addNewLikes(Integer id);
    @Transactional
    @Modifying
    @Query(value="update reply set new_like=new_like-1 where id=?1 and new_like>0", nativeQuery=true)
    void reduceNewLikes(Integer id);
    @Transactional
    @Modifying
    @Query(value="update article set new_reply=new_reply+1 where id=?1", nativeQuery=true)
    void addNewReply(Integer id);
    @Transactional
    @Modifying
    @Query(value="update article set new_reply_id=?2 where id=?1", nativeQuery=true)
    void setNewReplyId(Integer id,Integer id2);
    @Transactional
    @Modifying
    @Query(value="update article set new_like_id=?2 where id=?1", nativeQuery=true)
    void setNewLikeId(Integer id,Integer id2);
}
