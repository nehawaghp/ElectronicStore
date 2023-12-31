package com.example.demo_for_batch7.dtos;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;


    @Size
    private String name;

    private String email;

    private String password;

    private String gender;

    private String about;

    private String imageName;
}

