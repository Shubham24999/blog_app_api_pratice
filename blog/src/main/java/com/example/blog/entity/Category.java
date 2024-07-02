package com.example.blog.entity;



import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer categoryId;
    
    @Column(name = "category_title")
    private String categoriesTitle;
    
    @Column(name = "description_description")
    private String categoriesDescription;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "category_created_date_time")
    private LocalDateTime categoryCreateDateTime;
    
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Post> postList;
    
}
