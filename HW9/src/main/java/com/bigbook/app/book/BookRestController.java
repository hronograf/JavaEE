package com.bigbook.app.book;

import com.bigbook.app.book.dto.BookInfoDto;
import com.bigbook.app.book.dto.ChangeFavouritesRequestDto;
import com.bigbook.app.book.dto.CreateBookRequestDto;
import com.bigbook.app.book.dto.SearchBookResponseDto;
import com.bigbook.app.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RequiredArgsConstructor

@RestController
@RequestMapping("/books")
public class BookRestController {

    private final BookService bookService;

    @PreAuthorize("hasAuthority('ADD_NEW_BOOK')")
    @PostMapping("/create")
    public Response<Void> createBookPost(@Valid @RequestBody CreateBookRequestDto requestDto) {
        bookService.saveBook(requestDto);
        return Response.of("Successfully added", null);
    }

    @GetMapping("/search")
    public Response<SearchBookResponseDto> getFilteredByTitleBooks(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "isbn", defaultValue = "") String isbn,
            @RequestParam(value = "page", defaultValue = "0") int pageNumber
    ) {
        return Response.success(bookService.findBooks(title, isbn, pageNumber));
    }

    @PostMapping("/add-to-favourites")
    public void addToFavourites(@Valid @RequestBody ChangeFavouritesRequestDto requestDto) {
        bookService.addToFavourites(requestDto);
    }

    @PostMapping("/delete-from-favourites")
    public void deleteFromFavourites(@Valid @RequestBody ChangeFavouritesRequestDto requestDto) {
        bookService.deleteFromFavourites(requestDto);
    }

    @GetMapping("/favourite-info")
    public Response<Set<BookInfoDto>> getFavouriteBooks() {
        return Response.success(bookService.getFavouriteBooks());
    }

}
