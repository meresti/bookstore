package com.meresti.bookstore.bookservice;

import com.meresti.bookstore.bookservice.model.Book;
import com.meresti.bookstore.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class BookServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

    @Component
    private static class SampleDataCommandLineRunner implements CommandLineRunner {

        private final BookRepository bookRepository;

        @Autowired
        private SampleDataCommandLineRunner(BookRepository bookRepository) {
            this.bookRepository = bookRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            final Book designPatterns = Book.builder()
                    .title("Design Patterns: Elements of Reusable Object-Oriented Software")
                    .authors(Arrays.asList("John Vlissides", "Richard Helm", "Ralph Johnson", "Erich Gamma"))
                    .isbn("9780201633610")
                    .language("English")
                    .publisher("Addison-Wesley Professional")
                    .publicationDate(LocalDate.of(1994, Month.OCTOBER, 31))
                    .build();

            final Book cleanCode = Book.builder()
                    .title("Clean Code: A Handbook of Agile Software Craftsmanship")
                    .authors(Collections.singletonList("Robert C. Martin"))
                    .isbn("9780132350884")
                    .language("English")
                    .publisher("Prentice Hall")
                    .publicationDate(LocalDate.of(2008, Month.AUGUST, 1))
                    .build();

            final Book theCleanCoder = Book.builder()
                    .title("The Clean Coder: A Code of Conduct for Professional Programmers")
                    .authors(Collections.singletonList("Robert C. Martin"))
                    .isbn("9780137081073")
                    .language("English")
                    .publisher("Prentice Hall")
                    .publicationDate(LocalDate.of(2011, Month.MAY, 13))
                    .build();

            final Book effectiveJava = Book.builder()
                    .title("Effective Java - 2nd Edition")
                    .authors(Collections.singletonList("Joshua J. Bloch"))
                    .isbn("9780321356680")
                    .language("English")
                    .publisher("Addison-Wesley Professional")
                    .publicationDate(LocalDate.of(2008, Month.MAY, 8))
                    .build();

            bookRepository.save(Arrays.asList(designPatterns, cleanCode, theCleanCoder, effectiveJava));
        }
    }
}
