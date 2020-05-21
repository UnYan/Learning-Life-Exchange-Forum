package com.a.repository;

import com.a.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByUsername(String name);
    List<User> findByUsernameAndPassword(String name, String passwd);
}