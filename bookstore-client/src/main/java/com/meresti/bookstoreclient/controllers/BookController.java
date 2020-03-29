package com.meresti.bookstoreclient.controllers;

import com.meresti.bookstoreclient.service.BookReader;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("books")
public class BookController {

    private final BookReader bookReader;

    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @GetMapping("titles")
    public List<String> getBooks() {
        log.info("Retrieving book titles...");
        return circuitBreakerFactory.create("titles").run(
                () -> bookReader.read().stream()
                        .map(b -> b.getTitle() + " by " + String.join(", ", b.getAuthors()))
                        .collect(Collectors.toList()),
                this::fallback);
    }

    @CircuitBreaker(name = "titles-annotated", fallbackMethod = "fallback")
    @GetMapping("titles-annotated")
    public List<String> getBooksWithFallback() {
        log.info("Retrieving book titles (circuit breaker with annotation)...");
        return bookReader.read().stream()
                .map(b -> b.getTitle() + " by " + String.join(", ", b.getAuthors()))
                .collect(Collectors.toList());
    }

    public List<String> fallback(final Throwable t) {
        log.info("Using fallback to retrieve book titles.", t);
        return Collections.singletonList("Atlas Shrugged by Ayn Rand");
    }
}
