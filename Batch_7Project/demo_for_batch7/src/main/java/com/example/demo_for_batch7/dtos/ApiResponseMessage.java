package com.example.demo_for_batch7.dtos;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {

    private String message;

    private boolean success;

    private HttpStatus status;
}


