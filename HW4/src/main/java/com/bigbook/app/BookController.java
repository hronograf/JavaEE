package com.bigbook.app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping("/books/search")
    public List<BookModel> getFilteredBooks(@RequestParam("query") String query) {
        String lowerCaseQuery = query.toLowerCase();
        return bookRepository.getBooks().stream()
                .filter(bookModel -> bookModel.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        bookModel.getIsbn().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }
}
