package com.meresti.bookstoreclient.controllers;

import com.meresti.bookstoreclient.service.BookReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("books")
public class BookController {

    private final BookReader bookReader;

    @GetMapping("titles")
    public List<String> getBooks() {
        return bookReader.read().stream()
                .map(b -> b.getTitle() + " by " + String.join(", ", b.getAuthors()))
                .collect(Collectors.toList());
    }
}
