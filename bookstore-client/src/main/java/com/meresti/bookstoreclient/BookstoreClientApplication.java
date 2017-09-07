package com.meresti.bookstoreclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients
@EnableResourceServer
@EnableZuulProxy
@SpringBootApplication
public class BookstoreClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreClientApplication.class, args);
    }

}
