package com.example.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    
}
