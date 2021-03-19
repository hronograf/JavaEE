package com.bigbook.app.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookEntity> findBooks(String query) {
        String lowerCaseQuery = query.toLowerCase();
        return bookRepository.getAllBooks().stream()
                .filter(bookEntity -> bookEntity.getTitle().toLowerCase().contains(lowerCaseQuery)
                        || bookEntity.getIsbn().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }
}
