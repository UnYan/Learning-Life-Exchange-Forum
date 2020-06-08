package com.a.repository;

import com.a.entity.Notices;
import com.a.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer id);
    List<User> findByUsername(String name);
    List<User> findByUsernameAndPassword(String name, String passwd);
    List<User> findByEmail(String email);
    List<User> findByUsernameAndEmail(String name, String email);
    @Transactional
    @Modifying
    @Query(value="update user set exp=exp+?2 where id=?1", nativeQuery=true)
    void addExp(Integer id,Integer exp);
    @Transactional
    @Modifying
    @Query(value="update user set exp=exp-userlevel*10,userlevel=userlevel+1 where userlevel!=0 and id=?1 and exp>=userlevel*10", nativeQuery=true)
    void freshLevel(Integer id);
}