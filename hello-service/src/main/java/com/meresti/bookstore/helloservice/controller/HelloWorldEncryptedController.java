package com.meresti.bookstore.helloservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloWorldEncryptedController {

    private final String encryptedMessage;

    @Autowired
    public HelloWorldEncryptedController(@Value("${encrypted-message}") String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    @GetMapping("/encrypted-message")
    public String getEncryptedText() {
        return encryptedMessage;
    }

}
