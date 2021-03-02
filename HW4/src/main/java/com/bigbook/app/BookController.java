package com.bigbook.app;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

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
        return bookService.findBooks(query);
    }
}
