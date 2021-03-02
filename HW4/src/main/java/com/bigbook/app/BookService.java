package com.bigbook.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookModel> findBooks(String query) {
        String lowerCaseQuery = query.toLowerCase();
        return bookRepository.getBooks().stream()
                .filter(bookModel -> bookModel.getTitle().toLowerCase().contains(lowerCaseQuery)
                        || bookModel.getIsbn().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }
}
