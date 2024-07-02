package com.example.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.helper.ResponseEntity;
import com.example.blog.model.UserModel;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.UserService;

@RestController
@ResponseBody
@RequestMapping("/api")
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        return userService.getAllUser();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable Integer id) {
        return userService.getSingleUser(id);
    }

    @PostMapping("user/create")
    public ResponseEntity newUser(@RequestBody UserModel userData) {
        // System.out.println("data is"+ userData);
        return userService.createUser(userData);
    }

    @PostMapping("/user/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody UserModel userData) {

        ResponseEntity responseValue = new ResponseEntity();
        try {
            return userService.updateUserDetail(id, userData);
        } catch (Exception e) {
            responseValue.setMessage("User with that Id is not present.");
            responseValue.setStatus("NOT OK");
            return responseValue;
        }

    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        return userService.deleteUserData(id);
    }

}
