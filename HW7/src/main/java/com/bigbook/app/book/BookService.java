package com.bigbook.app.book;

import com.bigbook.app.book.dto.SearchBookResponseDto;
import com.bigbook.app.exceptions.BookAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.bigbook.app.book.BookEntity.isbnContains;
import static com.bigbook.app.book.BookEntity.titleIgnoreCaseContains;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public SearchBookResponseDto findBooks(String title, String isbn, int pageNumber) {
        Page<BookEntity> page = bookRepository.findAll(
                Specification.where(titleIgnoreCaseContains(title)).and(isbnContains(isbn)),
                PageRequest.of(pageNumber, BookEntity.BOOKS_PER_PAGE, Sort.by("title").and(Sort.by("isbn")))
        );
        int canBeShown = (int) page.getTotalElements() - (pageNumber + 1) * BookEntity.BOOKS_PER_PAGE;
        if (canBeShown < 0)
            canBeShown = 0;
        return new SearchBookResponseDto(page.getContent(), canBeShown);
    }

    public void saveBook(BookEntity bookEntity) {
        if (bookRepository.existsById(bookEntity.getIsbn()))
            throw new BookAlreadyExistsException();
        bookRepository.saveAndFlush(bookEntity);
    }
}
