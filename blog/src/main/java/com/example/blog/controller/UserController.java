package com.example.blog.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.blog.helper.RequestResponse;
import com.example.blog.model.LogInResponse;
import com.example.blog.model.LoginRequest;
import com.example.blog.model.UserModel;
import com.example.blog.service.UserService;

import org.springframework.util.StringUtils;

@RestController
// @RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RequestResponse> registerUser(@RequestBody UserModel userData) {

        RequestResponse response = new RequestResponse();
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
    public ResponseEntity<LogInResponse> loginUser(@RequestBody LoginRequest logInData) {

        LogInResponse response = new LogInResponse();
        if (!StringUtils.hasText(logInData.getEmail()) || !StringUtils.hasText(logInData.getPassword())) {
            response.setStatus("NOT OK");
            response.setMessage("Email and Password are required for login");
            return ResponseEntity.badRequest().body(response); // RETURN IMMEDIATELY
        }

        response = userService.loginUser(logInData);
        if (response.getStatus().equalsIgnoreCase("OK")) {
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("Something went wrong, please try again later");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Get details of the logged-in user
    // @GetMapping("/me")
    // public ResponseEntity<RequestResponse> getLoggedInUser(Authentication
    // authentication) {
    // String email = authentication.getName();
    // RequestResponse response = userService.getLoggedInUserProfile(email);
    // return ResponseEntity.ok(response);
    // }

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

    // Get user by ID - Admin can view all, User can only view themselves
    // @GetMapping("/{id}")
    // public ResponseEntity<RequestResponse> getUserById(@PathVariable Integer id,
    // Authentication authentication) {
    // String email = authentication.getName();
    // boolean isAdmin = authentication.getAuthorities().stream()
    // .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

    // RequestResponse userResponse = userService.getSingleUser(id);

    // if (!isAdmin && userResponse.getData() != null
    // && !email.equals(((UserModel) userResponse.getData()).getEmail())) {
    // RequestResponse deniedResponse = new RequestResponse("NOT OK", "Access
    // Denied", null);
    // return ResponseEntity.status(403).body(deniedResponse);
    // }

    // return ResponseEntity.ok(userResponse);
    // }

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
