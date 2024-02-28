package com.example.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.helper.ResponseEntity;
import com.example.blog.model.PostModel;
import com.example.blog.service.PostService;

@RestController
@RequestMapping("/api")
@ResponseBody
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/allposts")
    public ResponseEntity getPosts(){
        return postService.getAllPost();
    }
    
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity getUserPosts(@PathVariable Integer userId){
        return postService.getUsersPost(userId);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity getCategoryPosts(@PathVariable Integer categoryId){
        return postService.getCategoriesPost(categoryId);
    }

    @PostMapping("/post/create/user/{userId}/category/{categoryId}")
    public ResponseEntity createPost(@RequestBody PostModel postData,@PathVariable Integer userId,@PathVariable Integer categoryId){
        return postService.createNewPost(postData,userId,categoryId);
    }
    
}
