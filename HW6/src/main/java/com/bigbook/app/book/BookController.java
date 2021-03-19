package com.bigbook.app.book;

import lombok.RequiredArgsConstructor;
import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    @PostMapping("/books/create")
    public ResponseEntity<String> createBookPost(@RequestBody BookEntity bookEntity) {
        try {
            bookRepository.saveBook(BookEntity.builder()
                    .isbn(bookEntity.getIsbn())
                    .title(bookEntity.getTitle())
                    .author(bookEntity.getAuthor())
                    .build());
            return ResponseEntity.ok("{\"message\": \"Successfully added\"}");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("{\"message\": \"Book with this isbn has already been added\"}");
        }
    }

    @GetMapping("/books/all")
    public List<BookEntity> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @GetMapping("/books/search")
    public List<BookEntity> getFilteredBooks(@RequestParam("query") String query) {
        return bookService.findBooks(query);
    }
}
