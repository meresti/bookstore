package com.meresti.bookstoreclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients
//@EnableResourceServer
//@EnableZuulProxy
@SpringBootApplication
public class BookstoreClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreClientApplication.class, args);
    }

}
