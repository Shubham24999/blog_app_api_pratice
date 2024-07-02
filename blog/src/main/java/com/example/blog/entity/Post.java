package com.example.blog.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "post_data")
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;

    private String title;

    private String content;

    private String image;
    
    @Column(name = "post_created_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime postCreateDateTime;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") 
    private User user;

}





// @JsonBackReference
