package com.example.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.entity.Users;

@Repository
public interface PostRepository extends CrudRepository<Post,Integer>{

    List<Post> findByUser(Users user);

    List<Post> findByCategory(Category category);

    
    
}
