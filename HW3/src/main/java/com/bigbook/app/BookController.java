package com.bigbook.app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @PostMapping("/books/create")
    public String createBookPost(@RequestBody BookModel bookModel) {
        bookRepository.saveBook(bookModel);
        return "{\"message\": \"success\"}";
    }

    @GetMapping("/books/all")
    public List<BookModel> getAllBooks() {
        return bookRepository.getBooks();
    }
}
