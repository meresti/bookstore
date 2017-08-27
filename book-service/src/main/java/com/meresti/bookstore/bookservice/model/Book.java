package com.meresti.bookstore.bookservice.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> authors;

    @NotNull
    @Column(length = 13, nullable = false)
    @Size(max = 13)
    private String isbn;

    private String publisher;

    private LocalDate publicationDate;

    @Column(length = 20)
    @Size(max = 20)
    private String language;

}
