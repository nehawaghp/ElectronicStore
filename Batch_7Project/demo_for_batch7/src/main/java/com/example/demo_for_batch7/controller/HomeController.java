package com.example.demo_for_batch7.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {

    @GetMapping
    public String testString() {
        return "Welcome to electronic Store";
    }
}
