package com.example.blog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entity.User;
import com.example.blog.helper.ResponseEntity;
import com.example.blog.model.UserModel;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;

@Service
public class UserService {

    private final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public ResponseEntity getAllUser() {

        ResponseEntity responseValue = new ResponseEntity();

        List<User> userList = (List<User>) userRepository.findAll();

        try {
            // check that all the users are present or not.

            List<UserModel> userDataList = new ArrayList<>();

            if (userDataList.isEmpty()) {

                for (User userData : userList) {

                    UserModel userModelData = new UserModel();

                    userModelData.setId(userData.getId());
                    userModelData.setName(userData.getName());
                    userModelData.setEmail(userData.getEmail());
                    userModelData.setAbout(userData.getAbout());
                    userModelData.setUserCreateDateTime(userData.getUserCreateDateTime());



                    userDataList.add(userModelData);

                }
                responseValue.setStatus("OK");
                responseValue.setMessage("Users List");
                responseValue.setData(userDataList);

            } else {
                responseValue.setStatus("OK");
                responseValue.setMessage("Users List is empty.");
            }

        } catch (Exception e) {
            responseValue.setStatus("NOT OK");
            responseValue.setMessage("Something went Wrong: " + e.getMessage());
        }

        return responseValue;
    }

    public ResponseEntity getSingleUser(Integer id) {

        ResponseEntity responseValue = new ResponseEntity();
        Optional<User> userAvailable = userRepository.findById(id);

        if (userAvailable.isPresent()) {
            responseValue.setMessage("User Present");
            responseValue.setStatus("OK");
            responseValue.setData(userAvailable);

        } else {
            responseValue.setMessage("User not Present with Id");
            responseValue.setStatus("NOT OK");
        }

        return responseValue;
    }

    public ResponseEntity createUser(UserModel userData) {

        ResponseEntity responseValue = new ResponseEntity();
        try {

            User userDetails = new User();
            userDetails.setEmail(userData.getEmail());
            userDetails.setAbout(userData.getAbout());
            userDetails.setName(userData.getName());
            userDetails.setPassword(userData.getPassword());
            userDetails.setUserCreateDateTime(LocalDateTime.now());
            User savedUser = userRepository.save(userDetails);

            responseValue.setMessage("New User Created.");
            responseValue.setStatus("OK");
            responseValue.setData(savedUser);

        } catch (Exception e) {
            responseValue.setMessage("Error While Creating New User.");
            responseValue.setStatus("NOT OK");
        }

        return responseValue;

    }

    public ResponseEntity updateUserDetail(Integer id, UserModel userData) {

        ResponseEntity responseValue = new ResponseEntity();
        Optional<User> userAvailable = userRepository.findById(id);

        if (userAvailable.isPresent()) {
            User userToUpdate = userAvailable.get();

            // Check and update each field if provided in the UserModel
            if (userData.getName() != null) {
                userToUpdate.setName(userData.getName());
            }
            if (userData.getEmail() != null) {
                userToUpdate.setEmail(userData.getEmail());
            }
            if (userData.getPassword() != null) {
                userToUpdate.setPassword(userData.getPassword());
            }
            if (userData.getAbout() != null) {
                userToUpdate.setAbout(userData.getAbout());
            }

            // Save the updated user
            User savedUpdatedUser = userRepository.save(userToUpdate);

            responseValue.setMessage("User with that Id updated.");
            responseValue.setStatus("OK");
            responseValue.setData(savedUpdatedUser);
        } else {
            responseValue.setMessage("User with that Id not found.");
            responseValue.setStatus("NOT OK");
        }

        return responseValue;

    }

    public ResponseEntity deleteUserData(Integer id) {
        Optional<User> user = userRepository.findById(id);

        ResponseEntity responseValue = new ResponseEntity();
        if (user.isPresent()) {
            userRepository.deleteById(id);
            responseValue.setMessage("User deleted which Id is " + id);
            responseValue.setStatus("OK");
            responseValue.setData(user);

        } else {
            responseValue.setMessage("User not found with Id " + id);
            responseValue.setStatus("OK");

        }

        return responseValue;

    }

}
