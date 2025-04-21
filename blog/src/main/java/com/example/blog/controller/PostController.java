package com.example.blog.controller;

import com.example.blog.helper.RequestResponse;
import com.example.blog.model.PostModel;
import com.example.blog.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/all")
    public ResponseEntity<RequestResponse> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<RequestResponse> getUserPosts(@PathVariable Integer userId) {
        return ResponseEntity.ok(postService.getUsersPost(userId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<RequestResponse> getCategoryPosts(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(postService.getCategoriesPost(categoryId));
    }

    @PostMapping(value = "/create/user/{userId}/category/{categoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RequestResponse> createPost(
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        PostModel postData = new PostModel();
        postData.setTitle(title);
        postData.setContent(content);
        postData.setImageFile(imageFile);

        return ResponseEntity.ok(postService.createNewPost(postData, userId, categoryId));
    }
}
