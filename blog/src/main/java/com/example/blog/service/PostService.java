package com.example.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.helper.ResponseEntity;
import com.example.blog.model.CategoryModel;
import com.example.blog.model.PostModel;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;

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
            
            List<Post> postList=(List<Post>) postRepository.findAll();
            List<PostModel> postDataList=new ArrayList<>();

            if (postList.size()>0) {
                
                for(Post postData:postList){

                    PostModel postModel=new PostModel(null, null, null, null, null, null, null, null, null);
                    postModel.setPostId(postData.getPostId());
                    postModel.setTitle(postData.getTitle());
                    postModel.setContent(postData.getContent());
                    postModel.setImage(postData.getImage());
                    postModel.setUserId(postData.getUser().getId());
                    postModel.setCategoryId(postData.getCategory().getCategoryId());
                    postModel.setPostUploadDateTime(postData.getPostCreateDateTime());
                    postDataList.add(postModel);
                }
                response.setMessage("Posts List are.");
            
            }else{
                response.setMessage("Posts are empty.");
            }

            response.setStatus("OK");
            response.setData(postDataList);
        } catch (Exception e) {
            response.setStatus("NOT OK");
            response.setMessage("Something Went Wrong:{}" + e.getMessage());

        }
        return response;
    }

    public ResponseEntity getUsersPost(Integer userId) {
        ResponseEntity response = new ResponseEntity();

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            try {
                List<Post> userPosts = postRepository.findByUser(user);

                List<PostModel> userPostDataList = new ArrayList<>();

                for (Post userPostData : userPosts) {
                    PostModel userPost = new PostModel(userId, null, null, null, userId, null, null, userId, null);
                    userPost.setPostId(userPostData.getPostId());
                    userPost.setTitle(userPostData.getTitle());
                    userPost.setUserId(userPostData.getUser().getId());

                    Optional<Category> cat = categoryRepository.findById(userPostData.getCategory().getCategoryId());

                    if (cat.isPresent()) {
                        CategoryModel catModel = new CategoryModel();

                        catModel.setCategoryId(cat.get().getCategoryId());
                        catModel.setCategoriesTitle(cat.get().getCategoriesTitle());
                        catModel.setCategoriesDescription(cat.get().getCategoriesDescription());
                        catModel.setCategoryCreateDateTime(cat.get().getCategoryCreateDateTime());

                        userPost.setCategory(catModel);
                    }

                    userPost.setContent(userPostData.getContent());
                    userPost.setImage(userPostData.getImage());
                    userPost.setTitle(userPostData.getTitle());
                    userPost.setPostUploadDateTime(userPostData.getPostCreateDateTime());

                    userPostDataList.add(userPost);
                }

                // crate a arraylist
                // set that all the data related to that user in that model using instantiate
                // return that all data into setData

                response.setStatus("OK");
                response.setMessage("All Posts for User with ID " + userId + " are: ");
                response.setData(userPostDataList);
                logger.info("Posts for user with ID {}: {}", userId, userPosts);
            } catch (Exception e) {
                response.setStatus("NOT OK");
                response.setMessage(e.getMessage());
            }
        } else {
            response.setStatus("NOT OK");
            response.setMessage("User is not valid.");
        }

        return response;

    }

    public ResponseEntity getCategoriesPost(Integer categoryId) {
        ResponseEntity response = new ResponseEntity();

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category = categoryOptional.get();
        List<Post> categoryPosts = postRepository.findByCategory(category);
        if (categoryOptional.isPresent() && categoryPosts.size() > 0) {

            try {

                List<PostModel> categoryPostList = new ArrayList<>();

                for (Post categoryPost : categoryPosts) {
                    PostModel categoryData = new PostModel(categoryId, null, null, null, categoryId, null, null,
                            categoryId, null);
                    categoryData.setPostId(categoryPost.getPostId());
                    categoryData.setTitle(categoryPost.getTitle());
                    categoryData.setContent(categoryPost.getContent());
                    categoryData.setImage(categoryPost.getImage());
                    categoryData.setPostUploadDateTime(categoryPost.getPostCreateDateTime());

                    categoryPostList.add(categoryData);
                }

                response.setStatus("OK");
                response.setMessage("All Posts for User with ID " + categoryId + " are: ");
                response.setData(categoryPostList);
            } catch (Exception e) {
                response.setStatus("NOT OK");
                response.setMessage(e.getMessage());
            }
        } else {
            response.setStatus("OK");
            response.setMessage("Category with Id : " + categoryId + " is Not Present");
        }

        return response;

    }

    public ResponseEntity createNewPost(PostModel postData, Integer userId, Integer categoryId) {
        ResponseEntity response = new ResponseEntity();

        Post repoData = new Post();

        try {
            Optional<User> userOptional = userRepository.findById(userId);
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

            if (userOptional.isPresent() && categoryOptional.isPresent()) {
                repoData.setContent(postData.getContent());
                repoData.setTitle(postData.getTitle());
                repoData.setImage(postData.getImage());
                repoData.setPostCreateDateTime(LocalDateTime.now());
                repoData.setUser(userOptional.get());
                repoData.setCategory(categoryOptional.get());

                Post savedPost = postRepository.save(repoData);
                postData.setPostId(savedPost.getPostId());
                postData.setPostUploadDateTime(savedPost.getPostCreateDateTime());
                postData.setUserId(userId);
                postData.setCategoryId(categoryId);
                response.setStatus("OK");
                response.setMessage("New Post Created with ID: " + savedPost.getPostId());
                response.setData(postData);
            } else {
                response.setStatus("Not OK");
            }
        } catch (Exception e) {
            response.setStatus("NOT OK");
            response.setMessage(e.getMessage());
        }
        return response;

    }
}