package com.meresti.bookstoreclient.model.rest;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Book {

    private String title;

    private List<String> authors;

}
