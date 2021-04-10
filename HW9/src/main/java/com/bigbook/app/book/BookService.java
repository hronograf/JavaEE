package com.bigbook.app.book;

import com.bigbook.app.auth.jwt.JwtTokenService;
import com.bigbook.app.book.dto.BookInfoDto;
import com.bigbook.app.book.dto.ChangeFavouritesRequestDto;
import com.bigbook.app.book.dto.SearchBookResponseDto;
import com.bigbook.app.book.exceptions.BookAlreadyExistsException;
import com.bigbook.app.exceptions.BadRequestException;
import com.bigbook.app.user.UserEntity;
import com.bigbook.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Set;
import java.util.stream.Collectors;

import static com.bigbook.app.book.BookEntity.isbnContains;
import static com.bigbook.app.book.BookEntity.titleIgnoreCaseContains;

@Service
@RequiredArgsConstructor
public class BookService {

    public static int BOOKS_PER_PAGE = 10;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public SearchBookResponseDto findBooks(String title, String isbn, int pageNumber) {
        Page<BookEntity> page = bookRepository.findAll(
                Specification.where(titleIgnoreCaseContains(title)).and(isbnContains(isbn)),
                PageRequest.of(pageNumber, BOOKS_PER_PAGE, Sort.by("title").and(Sort.by("isbn")))
        );
        int amountNotShown = (int) page.getTotalElements() - (pageNumber + 1) * BOOKS_PER_PAGE;
        if (amountNotShown < 0)
            amountNotShown = 0;
        return new SearchBookResponseDto(
                page.getContent().stream()
                .map(BookInfoDto::new)
                .collect(Collectors.toList()),
                amountNotShown
        );
    }

    public void saveBook(BookEntity bookEntity) {
        if (bookRepository.existsById(bookEntity.getIsbn()))
            throw new BookAlreadyExistsException();
        bookRepository.saveAndFlush(bookEntity);
    }

    public void addToFavourites(ChangeFavouritesRequestDto requestDto) {
        UserEntity contextUser = userRepository.findByIdWithFetchFavouriteBooks(JwtTokenService.getCurrentUserId()).orElseThrow();
        contextUser.getFavoriteBooks().add(new BookEntity(requestDto.getIsbn()));
        userRepository.saveAndFlush(contextUser);
    }

    public Set<BookInfoDto> getFavouriteBooks() {
        UserEntity contextUser = userRepository.findByIdWithFetchFavouriteBooks(JwtTokenService.getCurrentUserId()).orElseThrow();
        return contextUser.getFavoriteBooks().stream()
                .map(BookInfoDto::new)
                .collect(Collectors.toSet());
    }

    public void getBookInfo(String isbn, Model model) {
        UserEntity contextUser = userRepository.findByIdWithFetchFavouriteBooks(JwtTokenService.getCurrentUserId()).orElseThrow();
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow(BadRequestException::new);
        model.addAttribute("book", bookEntity);
        boolean isBookFavourite = contextUser.getFavoriteBooks().stream()
                .anyMatch(favourite -> favourite.getIsbn().equals(bookEntity.getIsbn()));
        model.addAttribute("isFavourite", isBookFavourite);
    }

    public void deleteFromFavourites(ChangeFavouritesRequestDto requestDto) {
        UserEntity contextUser = userRepository.findByIdWithFetchFavouriteBooks(JwtTokenService.getCurrentUserId()).orElseThrow();
        contextUser.getFavoriteBooks().removeIf(book -> book.getIsbn().equals(requestDto.getIsbn()));
        userRepository.saveAndFlush(contextUser);
    }
}
