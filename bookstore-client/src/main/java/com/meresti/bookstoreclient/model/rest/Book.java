package com.meresti.bookstoreclient.model.rest;

import lombok.Data;

import java.util.List;

@Data
public class Book {

    private String title;

    private List<String> authors;

}
