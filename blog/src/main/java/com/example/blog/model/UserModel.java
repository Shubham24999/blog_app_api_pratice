package com.example.blog.model;

import java.time.LocalDateTime;
import java.util.List;
import com.example.blog.entity.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserModel {

    private Integer id;
    
    private String name;
    
    private String email;
    
    private String password;
    
    private String about;

    public Long userCreateDateTime;

    private List<Post> postList;
    
}
