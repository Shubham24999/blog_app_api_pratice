package com.example.blog.model;

import java.util.List;
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
    private String about;
    private String role;
    private String password;
    private Long userCreateDateTime;
    private List<PostModel> postList;

}
