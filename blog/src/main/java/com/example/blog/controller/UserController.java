// package com.example.blog.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.example.blog.helper.RequestResponse;
// import com.example.blog.model.UserModel;
// import com.example.blog.service.UserService;

// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.Authentication;

// @RestController
// @RequestMapping("/api/users")
// public class UserController {

//     // @Autowired
//     // private UserService userService;

//     // Get details of the logged-in user
//     @GetMapping("/me")
//     public ResponseEntity<RequestResponse> getLoggedInUser(Authentication authentication) {
//         String email = authentication.getName();
//         RequestResponse response = userService.getLoggedInUserProfile(email);
//         return ResponseEntity.ok(response);
//     }

//     // Get all users - Only Admin
//     @GetMapping
//     // @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<RequestResponse> getAllUsers() {
//         RequestResponse response = userService.getAllUsers();
//         return ResponseEntity.ok(response);
//     }

//     // Get user by ID - Admin can view all, User can only view themselves
//     @GetMapping("/{id}")
//     public ResponseEntity<RequestResponse> getUserById(@PathVariable Integer id, Authentication authentication) {
//         String email = authentication.getName();
//         boolean isAdmin = authentication.getAuthorities().stream()
//                 .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

//         RequestResponse userResponse = userService.getSingleUser(id);

//         if (!isAdmin && userResponse.getData() != null
//                 && !email.equals(((UserModel) userResponse.getData()).getEmail())) {
//             RequestResponse deniedResponse = new RequestResponse("NOT OK", "Access Denied", null);
//             return ResponseEntity.status(403).body(deniedResponse);
//         }

//         return ResponseEntity.ok(userResponse);
//     }

//     // Update user
//     @PutMapping("/{id}")
//     public ResponseEntity<RequestResponse> updateUser(@PathVariable Integer id,
//             @RequestBody UserModel userData,
//             Authentication authentication) {
//         String email = authentication.getName();
//         boolean isAdmin = authentication.getAuthorities().stream()
//                 .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

//         RequestResponse response = userService.updateUserDetail(id, userData, email, isAdmin);
//         return ResponseEntity.ok(response);
//     }

//     // Delete user
//     @DeleteMapping("/{id}")
//     public ResponseEntity<RequestResponse> deleteUser(@PathVariable Integer id, Authentication authentication) {
//         String email = authentication.getName();
//         boolean isAdmin = authentication.getAuthorities().stream()
//                 .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

//         RequestResponse response = userService.deleteUser(id, email, isAdmin);
//         return ResponseEntity.ok(response);
//     }
// }
