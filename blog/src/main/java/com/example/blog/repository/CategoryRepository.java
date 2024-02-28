package com.example.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer>{
    
}
