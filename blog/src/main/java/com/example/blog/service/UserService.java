package com.example.blog.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Users;
import com.example.blog.helper.RequestResponse;
import com.example.blog.model.SignUpLogInResponse;
import com.example.blog.model.LoginRequest;
import com.example.blog.model.UserModel;
import com.example.blog.repository.UserRepository;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtUtilsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public SignUpLogInResponse registerUser(UserModel userData) {

        SignUpLogInResponse response = new SignUpLogInResponse();
        if (userRepository.existsByEmail(userData.getEmail())) {
            response.setStatus("OK");
            response.setMessage("User already exists with this email");
            return response;
        } else {
            Users newUser = new Users();
            newUser.setEmail(userData.getEmail());

            newUser.setPassword(passwordEncoder.encode(userData.getPassword()));
            newUser.setName(userData.getName());
            newUser.setAbout(userData.getAbout());
            newUser.setUserCreateDateTimeEpoch(java.time.Instant.now().getEpochSecond());
            // newUser.setUserRole(userData.getRole());
            String token = jwtUtilsService.generateToken(userData.getEmail());
            response.setToken(token);
            response.setData(userRepository.save(newUser));
            response.setStatus("OK");
            response.setMessage("User registered successfully");
        }

        return response;
    }

    public RequestResponse getAllUsers() {
        RequestResponse response = new RequestResponse();
        response.setData(userRepository.findAll());
        response.setStatus("OK");
        response.setMessage("All users fetched successfully");
        return response;
    }

    public SignUpLogInResponse loginUser(LoginRequest logInData) {
        SignUpLogInResponse response = new SignUpLogInResponse();
        Optional<Users> user = userRepository.findByEmail(logInData.getEmail());
        logger.info("User fetch status: " + user.isEmpty());
        if (user.isEmpty()) {
            response.setStatus("OK");
            response.setMessage("User not found with this email");
            return response;
        } else {

            // this should be-- authenticationManager where we have declare with name like
            // in Security Config
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(logInData.getEmail(), logInData.getPassword()));

            if (authentication.isAuthenticated()) {

                String token = jwtUtilsService.generateToken(logInData.getEmail());
                response.setToken(token);
                response.setStatus("OK");
                response.setMessage("User logged in successfully");
            } else {
                response.setStatus("Not OK");
                response.setMessage("Invalid credentials");
            }
        }
        return response;
    }

}
