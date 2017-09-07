package com.meresti.bookstoreclient.service;


import com.meresti.bookstoreclient.model.rest.Book;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BookReaderFallback implements BookReader {

    @Override
    public List<Book> read() {
        final Book atlasShrugged = Book.builder()
                .title("Atlas Shrugged")
                .authors(Collections.singletonList("Ayn Rand"))
                .build();

        return Collections.singletonList(atlasShrugged);
    }

}
