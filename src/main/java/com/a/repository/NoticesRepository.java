package com.a.repository;

import com.a.entity.Notices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticesRepository extends JpaRepository<Notices,Integer> {
    @Query(value="select distinct * from notices where toid=?1 and flag=0 order by id DESC ", nativeQuery=true)
    List<Notices> findLike(Integer id);
    @Query(value="select distinct * from notices where toid=?1 and flag=1 order by id DESC ", nativeQuery=true)
    List<Notices> findReply(Integer id);
}
