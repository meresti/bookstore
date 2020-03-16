package com.meresti.bookstore.authservice;

import com.meresti.bookstore.authservice.model.User;
import com.meresti.bookstore.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@EnableDiscoveryClient
//@EnableResourceServer
@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    private static class UserCommandLineRunner implements CommandLineRunner {

        private final UserRepository userRepository;

        @Override
        public void run(String... args) throws Exception {
            Stream.of("zsolt:alma", "john:korte", "sarah:szilva")
                    .map(x -> x.split(":"))
                    .map(this::createUser)
                    .forEach(userRepository::save);
        }

        private User createUser(String[] tuple) {
            return User.builder()
                    .username(tuple[0])
                    .password("{noop}" + tuple[1])
                    .active(true)
                    .build();
        }
    }

}
