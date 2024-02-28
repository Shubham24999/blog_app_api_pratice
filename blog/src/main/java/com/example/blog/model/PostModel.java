package com.example.blog.model;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.blog.entity.Category;
import com.example.blog.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PostModel {
    
    private Integer postId;

    private String title;

    private String content;

    private String image;
    // will give error, we will have to use CategoryModel and UserModel.
    private Integer categoryId;

    private CategoryModel category;

    private UserModel user;
    
    private Integer userId;

    private LocalDateTime postUploadDateTime;
    
}
