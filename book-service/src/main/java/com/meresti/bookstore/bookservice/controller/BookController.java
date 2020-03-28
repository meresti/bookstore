package com.meresti.bookstore.bookservice.controller;

import com.meresti.bookstore.bookservice.model.Book;
import com.meresti.bookstore.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("books")
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        log.info("Retrieving all books...");
        return ResponseEntity.ok().body(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable Long id) {
        log.info("Retrieving book with identifier {}...", id);
        final Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook
                .map(book -> ResponseEntity.ok().body(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> insertOne(@Valid @RequestBody Book book) {
        log.info("Adding new book {}...", book.getIsbn());
        final Book savedBook = bookRepository.save(book);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateOne(@PathVariable Long id, @Valid @RequestBody Book book) {
        log.info("Updating book with identifier {}...", id);
        final Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate.isPresent()) {
            book.setId(id);
            final Book updatedBook = bookRepository.save(book);
            return ResponseEntity.ok().body(updatedBook);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable Long id) {
        log.info("Deleting book with identifier {}...", id);
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
