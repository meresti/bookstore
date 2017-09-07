package com.meresti.bookstore.bookservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloWorldController {

    private final String message;

    @Autowired
    public HelloWorldController(@Value("${message}") String message) {
        this.message = message;
    }

    @GetMapping("/message")
    public String sayHello() {
        return message;
    }
}
