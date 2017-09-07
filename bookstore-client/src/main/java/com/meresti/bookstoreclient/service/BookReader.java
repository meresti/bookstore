package com.meresti.bookstoreclient.service;

import com.meresti.bookstoreclient.model.rest.Book;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
@FeignClient("book-service")
public interface BookReader {

    @GetMapping("/books")
    List<Book> read();

}
