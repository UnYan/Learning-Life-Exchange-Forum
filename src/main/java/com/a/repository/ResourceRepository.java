package com.a.repository;
import com.a.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    Resource findResourceById(Integer id);
}
