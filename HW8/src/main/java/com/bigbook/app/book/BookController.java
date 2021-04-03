package com.bigbook.app.book;

import com.bigbook.app.book.dto.SearchBookResponseDto;
import com.bigbook.app.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/books/search")
    public Response<SearchBookResponseDto> getFilteredByTitleBooks(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "isbn", defaultValue = "") String isbn,
            @RequestParam(value = "page", defaultValue = "0") int pageNumber
    ) {
        return Response.success(bookService.findBooks(title, isbn, pageNumber));
    }

}
