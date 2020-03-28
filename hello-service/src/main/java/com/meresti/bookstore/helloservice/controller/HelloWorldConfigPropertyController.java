package com.meresti.bookstore.helloservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelloWorldConfigPropertyController {

    private final HelloWorldProperties helloWorldProperties;

    @GetMapping("/message-config-property")
    public String sayHello() {
        return helloWorldProperties.getMessage();
    }

}
