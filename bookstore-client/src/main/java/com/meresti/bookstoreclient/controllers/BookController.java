package com.meresti.bookstoreclient.controllers;

import com.meresti.bookstoreclient.service.BookReader;
import com.meresti.bookstoreclient.service.BookReaderWithoutFallback;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("books")
public class BookController {

    private final BookReader bookReader;
    private final BookReaderWithoutFallback bookReaderWithoutFallback;

    @GetMapping("titles")
    public List<String> getBooks() {
        return bookReader.read().stream()
                .map(b -> b.getTitle() + " by " + String.join(", ", b.getAuthors()))
                .collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("titles-controller-fallback")
    public List<String> getBooksWithFallback() {
        return bookReaderWithoutFallback.read().stream()
                .map(b -> b.getTitle() + " by " + String.join(", ", b.getAuthors()))
                .collect(Collectors.toList());
    }

    public List<String> fallback() {
        return Collections.singletonList("Atlas Shrugged by Ayn Rand");
    }
}
