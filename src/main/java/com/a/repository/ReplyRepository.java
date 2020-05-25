package com.a.repository;

import com.a.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Integer> {
    Reply findReplyById(int id);
    List<Reply> findByArticleid(int id);
    List<Reply> findByReplyid(int id);
}
