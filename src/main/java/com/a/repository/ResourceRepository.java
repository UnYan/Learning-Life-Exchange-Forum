package com.a.repository;
import com.a.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    Resource findResourceById(Integer id);
    List<Resource> findResourceByType(String type);
    List<Resource> findResourceByAuthorContaining(String type);
    List<Resource> findResourceByTitleContaining(String type);
}
