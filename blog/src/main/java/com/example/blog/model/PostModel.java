package com.example.blog.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostModel {
    private Integer postId;
    private String title;
    private String content;

    // Only for sending to frontend
    private String imageBase64;

    private Integer categoryId;
    private CategoryModel category;

    private Integer userId;
    private UserModel user;

    private LocalDateTime postUploadDateTime;

    // Only for receiving image while uploading
    private transient MultipartFile imageFile;
}

