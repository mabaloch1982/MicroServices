package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/demo")
public class Democontroller {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World Time: " + LocalDateTime.now();
    }
}
