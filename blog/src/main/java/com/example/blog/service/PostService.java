package com.example.blog.service;

import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.helper.RequestResponse;
import com.example.blog.model.CategoryModel;
import com.example.blog.model.PostModel;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class PostService {

    private static final Logger logger = LogManager.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public RequestResponse getAllPost() {
        RequestResponse response = new RequestResponse();
        try {
            List<Post> postList = (List<Post>) postRepository.findAll();
            List<PostModel> postModels = new ArrayList<>();

            for (Post post : postList) {
                postModels.add(mapPostToModel(post));
            }

            response.setStatus("OK");
            response.setMessage(postModels.isEmpty() ? "No posts found." : "Posts fetched successfully.");
            response.setData(postModels);
        } catch (Exception e) {
            logger.error("Error in getAllPost: ", e);
            response.setStatus("NOT OK");
            response.setMessage("Something went wrong: " + e.getMessage());
        }
        return response;
    }

    public RequestResponse getUsersPost(Integer userId) {
        RequestResponse response = new RequestResponse();
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                response.setStatus("NOT OK");
                response.setMessage("User not found with ID: " + userId);
                return response;
            }

            List<Post> posts = postRepository.findByUser(userOpt.get());
            List<PostModel> models = new ArrayList<>();

            for (Post post : posts) {
                PostModel model = mapPostToModel(post);
                model.setUserId(userId);

                Category category = post.getCategory();
                if (category != null) {
                    CategoryModel catModel = new CategoryModel(
                            category.getCategoryId(),
                            category.getCategoriesTitle(),
                            category.getCategoriesDescription(),
                            category.getCategoryCreateDateTimeEpoch());
                    model.setCategory(catModel);
                }

                models.add(model);
            }

            response.setStatus("OK");
            response.setMessage("Fetched posts for user ID: " + userId);
            response.setData(models);

        } catch (Exception e) {
            logger.error("Error in getUsersPost: ", e);
            response.setStatus("NOT OK");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    public RequestResponse getCategoriesPost(Integer categoryId) {
        RequestResponse response = new RequestResponse();
        try {
            Optional<Category> catOpt = categoryRepository.findById(categoryId);
            if (catOpt.isEmpty()) {
                response.setStatus("NOT OK");
                response.setMessage("Category not found with ID: " + categoryId);
                return response;
            }

            List<Post> posts = postRepository.findByCategory(catOpt.get());
            List<PostModel> models = new ArrayList<>();

            for (Post post : posts) {
                PostModel model = mapPostToModel(post);
                model.setCategoryId(categoryId);
                models.add(model);
            }

            response.setStatus("OK");
            response.setMessage("Fetched posts for category ID: " + categoryId);
            response.setData(models);

        } catch (Exception e) {
            logger.error("Error in getCategoriesPost: ", e);
            response.setStatus("NOT OK");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    public RequestResponse createNewPost(PostModel postData, Integer userId, Integer categoryId) {
        RequestResponse response = new RequestResponse();
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);

            if (userOpt.isEmpty() || categoryOpt.isEmpty()) {
                response.setStatus("NOT OK");
                response.setMessage("Invalid user or category ID.");
                return response;
            }

            Post newPost = new Post();
            newPost.setTitle(postData.getTitle());
            newPost.setContent(postData.getContent());
            newPost.setPostCreateDateTimeEpoch(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            newPost.setUser(userOpt.get());
            newPost.setCategory(categoryOpt.get());

            if (postData.getImageFile() != null && !postData.getImageFile().isEmpty()) {
                newPost.setImage(postData.getImageFile().getBytes());
            }

            Post savedPost = postRepository.save(newPost);

            // Prepare response data
            postData.setPostId(savedPost.getPostId());
            postData.setPostUploadDateTimeEpoch(savedPost.getPostCreateDateTimeEpoch());
            postData.setUserId(userId);
            postData.setCategoryId(categoryId);
            if (savedPost.getImage() != null) {
                postData.setImageBase64(Base64.getEncoder().encodeToString(savedPost.getImage()));
            }

            response.setStatus("OK");
            response.setMessage("Post created successfully with ID: " + savedPost.getPostId());
            response.setData(postData);

        } catch (Exception e) {
            logger.error("Error in createNewPost: ", e);
            response.setStatus("NOT OK");
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

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
        model.setPostUploadDateTimeEpoch(post.getPostCreateDateTimeEpoch());

        return model;
    }
}
