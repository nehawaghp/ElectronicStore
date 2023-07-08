package com.example.demo_for_batch7.dtos;

import com.example.demo_for_batch7.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;


    @Size(min = 3, max=15, message = "Invalid Name !!")
    private String name;

    //@Email (message =  "Invalid User Email !!")
    @NotBlank(message = "password is required !!")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9.-]+@([-a-z0-9]+\\.)+[a-z]{2,5}$" ,message = "Invalid User Email")
    private String email;

    @NotBlank(message = "password is required !!")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid gender !!")
    private String gender;

    @NotBlank(message = "Write something about yourself !!")
    private String about;

    @ImageNameValid
    private String imageName;

    //@Pattern
    // custom validator
}

