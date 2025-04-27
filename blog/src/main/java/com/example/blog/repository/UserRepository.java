package com.example.blog.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.Users;

@Repository
public interface UserRepository extends CrudRepository<Users,Integer> {

    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);
    
    
}
