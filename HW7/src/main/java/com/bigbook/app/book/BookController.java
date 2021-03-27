package com.bigbook.app.book;

import com.bigbook.app.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    @PostMapping("/books/create")
    public Response<String> createBookPost(@RequestBody BookEntity bookEntity) {
        bookService.saveBook(bookEntity);
        return Response.of("Successfully added", null);
    }

    @GetMapping("/books/all")
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/search")
    public List<BookEntity> getFilteredByTitleBooks(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "isbn", defaultValue = "") String isbn
    ) {
        return bookService.findBooks(title, isbn);
    }

}
