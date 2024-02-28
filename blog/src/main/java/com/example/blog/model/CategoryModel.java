package com.example.blog.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryModel {

    private Integer categoryId;
    
    private String categoriesTitle;
    
    private String categoriesDescription;

    private LocalDateTime categoryCreateDateTime;
    
}
