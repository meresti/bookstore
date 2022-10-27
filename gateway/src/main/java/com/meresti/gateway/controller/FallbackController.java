package com.meresti.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("fallback")
public class FallbackController {

    @GetMapping
    public Mono<String> fallback(final Throwable t) {
        log.info("Error forwarding call to desired microservice.", t);
        return Mono.just("Error forwarding call to desired microservice.");
    }

}
