package com.bigbook.app.book;

import com.bigbook.app.exceptions.BookAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookEntity> findBooks(String title, String isbn) {
        String lowerCaseTitle = title.toLowerCase();
        return bookRepository.findByTitleIgnoreCaseContainsAndIsbnContains(lowerCaseTitle, isbn);
    }

    public void saveBook(BookEntity bookEntity) {
        if (bookRepository.existsById(bookEntity.getIsbn()))
            throw new BookAlreadyExistsException();
        bookRepository.saveAndFlush(bookEntity);
    }
}
