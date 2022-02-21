package com.carrotfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hello")
public class HelloController {
    @GetMapping("/v1")
    public String test(){
        return "hello v1";
    }
}
