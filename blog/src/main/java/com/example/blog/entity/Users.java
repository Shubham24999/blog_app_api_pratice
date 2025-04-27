package com.example.blog.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    // columnDefinition = "TEXT"
    @Column(name = "password", nullable = false)
    private String password;

    private String about;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    // private LocalDateTime userCreateDateTime;
    @Column(name = "user_created_date_time_epoch")
    private Long userCreateDateTimeEpoch;

    // @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // @JoinTable(name = "user_role_mapping", // the name of the join table
    //         joinColumns = @JoinColumn(name = "user_id"), // column in join table referring to User
    //         inverseJoinColumns = @JoinColumn(name = "role_id") // column in join table referring to UserRole
    // )
    // private List<UserRole> userRole = new ArrayList();


}
