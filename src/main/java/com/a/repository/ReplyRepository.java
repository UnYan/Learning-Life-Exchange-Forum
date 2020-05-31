package com.a.repository;

import com.a.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Integer> {
    Reply findReplyById(int id);
    List<Reply> findByArticleid(int id);
    List<Reply> findByReplyid(int id);
    @Transactional
    @Modifying
    @Query(value="update reply set likes=likes+1 where id=?1", nativeQuery=true)
    void addLikes(Integer id);
    @Transactional
    @Modifying
    @Query(value="update reply set new_like=new_like+1 where id=?1", nativeQuery=true)
    void addNewLikes(Integer id);
    @Transactional
    @Modifying
    @Query(value="update reply set new_reply=new_reply+1 where id=?1", nativeQuery=true)
    void addNewReply(Integer id);
    @Transactional
    @Modifying
    @Query(value="update reply set new_reply_id=?2 where id=?1", nativeQuery=true)
    void setNewReplyId(Integer id,Integer id2);
    @Transactional
    @Modifying
    @Query(value="update reply set new_like_id=?2 where id=?1", nativeQuery=true)
    void setNewLikeId(Integer id,Integer id2);
}
