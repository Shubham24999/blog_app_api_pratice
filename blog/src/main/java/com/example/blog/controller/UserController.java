package com.example.blog.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.blog.helper.RequestResponse;
import com.example.blog.model.SignUpLogInResponse;
import com.example.blog.model.LoginRequest;
import com.example.blog.model.UserModel;
import com.example.blog.service.UserService;

import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<SignUpLogInResponse> registerUser(@RequestBody UserModel userData) {

        SignUpLogInResponse response = new SignUpLogInResponse();
        if (!StringUtils.hasText(userData.getEmail()) || !StringUtils.hasText(userData.getPassword())) {
            response.setStatus("NOT OK");
            response.setMessage("Email and Password are required");
            return ResponseEntity.badRequest().body(response); // RETURN IMMEDIATELY
        }

        response = userService.registerUser(userData);

        if (response.getStatus().equalsIgnoreCase("OK")) {
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("Something went wrong, please try again later");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<SignUpLogInResponse> loginUser(@RequestBody LoginRequest logInData) {

        SignUpLogInResponse response = new SignUpLogInResponse();
        if (!StringUtils.hasText(logInData.getEmail()) || !StringUtils.hasText(logInData.getPassword())) {
            response.setStatus("NOT OK");
            response.setMessage("Email and Password are required for login");
            return ResponseEntity.badRequest().body(response);
        }

        response = userService.loginUser(logInData);
        if (response.getStatus().equalsIgnoreCase("OK")) {
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("Something went wrong, please try again later");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Get all users - Only Admin
    @GetMapping("/get-users")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RequestResponse> getAllUsers() {
        RequestResponse response = userService.getAllUsers();
        if (response.getStatus().equalsIgnoreCase("OK")) {
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("Something went wrong, please try again later");
            return ResponseEntity.badRequest().body(response);
        }

    }



    // Update user
    // @PutMapping("/{id}")
    // public ResponseEntity<RequestResponse> updateUser(@PathVariable Integer id,
    // @RequestBody UserModel userData,
    // Authentication authentication) {
    // String email = authentication.getName();
    // boolean isAdmin = authentication.getAuthorities().stream()
    // .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

    // RequestResponse response = userService.updateUserDetail(id, userData, email,
    // isAdmin);
    // return ResponseEntity.ok(response);
    // }

    // Delete user
    // @DeleteMapping("/{id}")
    // public ResponseEntity<RequestResponse> deleteUser(@PathVariable Integer id,
    // Authentication authentication) {
    // String email = authentication.getName();
    // boolean isAdmin = authentication.getAuthorities().stream()
    // .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

    // RequestResponse response = userService.deleteUser(id, email, isAdmin);
    // return ResponseEntity.ok(response);
    // }
}
