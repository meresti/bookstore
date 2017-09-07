package com.meresti.bookstoreclient.service;

import com.meresti.bookstoreclient.model.rest.Book;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookReaderWithoutFallback {

    @GetMapping("/books")
    List<Book> read();

}
