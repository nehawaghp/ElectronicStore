package com.example.demo_for_batch7.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {


    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password", length = 100)
    private String password;

    private String gender;

    @Column(length = 10)
    private String about;

    @Column(name = "user_image_name")
    private String imageName;
}