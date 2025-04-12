package com.example.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Base64;
    
import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.helper.ResponseEntity;
import com.example.blog.model.CategoryModel;
import com.example.blog.model.PostModel;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    Logger logger = LogManager.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity getAllPost() {
        ResponseEntity response = new ResponseEntity();
        try {
            List<Post> postList = (List<Post>) postRepository.findAll();
            List<PostModel> postDataList = new ArrayList<>();

            for (Post postData : postList) {
                postDataList.add(mapPostToModel(postData));
            }

            response.setStatus("OK");
            response.setMessage(postList.isEmpty() ? "Posts are empty." : "Posts fetched successfully.");
            response.setData(postDataList);
        } catch (Exception e) {
            logger.error("Error in getAllPost: ", e);
            response.setStatus("NOT OK");
            response.setMessage("Something went wrong: " + e.getMessage());
        }
        return response;
    }

    public ResponseEntity getUsersPost(Integer userId) {
        ResponseEntity response = new ResponseEntity();
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                List<Post> userPosts = postRepository.findByUser(userOptional.get());
                List<PostModel> userPostDataList = new ArrayList<>();

                for (Post post : userPosts) {
                    PostModel postModel = mapPostToModel(post);
                    postModel.setUserId(userId);

                    Optional<Category> cat = categoryRepository.findById(post.getCategory().getCategoryId());
                    cat.ifPresent(category -> {
                        CategoryModel catModel = new CategoryModel(
                            category.getCategoryId(),
                            category.getCategoriesTitle(),
                            category.getCategoriesDescription(),
                            category.getCategoryCreateDateTime()
                        );
                        postModel.setCategory(catModel);
                    });

                    userPostDataList.add(postModel);
                }

                response.setStatus("OK");
                response.setMessage("Posts fetched for User ID: " + userId);
                response.setData(userPostDataList);
            } else {
                response.setStatus("NOT OK");
                response.setMessage("User with ID " + userId + " not found.");
            }
        } catch (Exception e) {
            logger.error("Error in getUsersPost: ", e);
            response.setStatus("NOT OK");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    public ResponseEntity getCategoriesPost(Integer categoryId) {
        ResponseEntity response = new ResponseEntity();
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

            if (categoryOptional.isPresent()) {
                List<Post> categoryPosts = postRepository.findByCategory(categoryOptional.get());

                List<PostModel> categoryPostList = new ArrayList<>();
                for (Post post : categoryPosts) {
                    PostModel model = mapPostToModel(post);
                    model.setCategoryId(categoryId);
                    categoryPostList.add(model);
                }

                response.setStatus("OK");
                response.setMessage("Posts fetched for Category ID: " + categoryId);
                response.setData(categoryPostList);
            } else {
                response.setStatus("NOT OK");
                response.setMessage("Category with ID " + categoryId + " not found.");
            }
        } catch (Exception e) {
            logger.error("Error in getCategoriesPost: ", e);
            response.setStatus("NOT OK");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    public ResponseEntity createNewPost(PostModel postData, Integer userId, Integer categoryId) {
        ResponseEntity response = new ResponseEntity();
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
    
            if (userOptional.isPresent() && categoryOptional.isPresent()) {
                Post repoData = new Post();
                repoData.setTitle(postData.getTitle());
                repoData.setContent(postData.getContent());
                repoData.setPostCreateDateTime(LocalDateTime.now());
                repoData.setUser(userOptional.get());
                repoData.setCategory(categoryOptional.get());
    
                if (postData.getImageFile() != null && !postData.getImageFile().isEmpty()) {
                    repoData.setImage(postData.getImageFile().getBytes());
                }
    
                Post savedPost = postRepository.save(repoData);
    
                postData.setPostId(savedPost.getPostId());
                postData.setPostUploadDateTime(savedPost.getPostCreateDateTime());
                postData.setUserId(userId);
                postData.setCategoryId(categoryId);
                postData.setImageBase64(Base64.getEncoder().encodeToString(savedPost.getImage()));
    
                response.setStatus("OK");
                response.setMessage("Post created successfully with ID: " + savedPost.getPostId());
                response.setData(postData);
            } else {
                response.setStatus("NOT OK");
                response.setMessage("Invalid User ID or Category ID");
            }
        } catch (Exception e) {
            logger.error("Error in createNewPost: ", e);
            response.setStatus("NOT OK");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }
    
    // Helper method to map Post entity to PostModel
    private PostModel mapPostToModel(Post post) {
        PostModel model = new PostModel();
        model.setPostId(post.getPostId());
        model.setTitle(post.getTitle());
        model.setContent(post.getContent());
    
        if (post.getImage() != null) {
            model.setImageBase64(Base64.getEncoder().encodeToString(post.getImage()));
        }
    
        model.setUserId(post.getUser().getId());
        model.setCategoryId(post.getCategory().getCategoryId());
        model.setPostUploadDateTime(post.getPostCreateDateTime());
        return model;
    }
    


}
