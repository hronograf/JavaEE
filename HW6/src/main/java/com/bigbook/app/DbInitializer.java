package com.bigbook.app;

import com.bigbook.app.book.BookEntity;
import com.bigbook.app.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbInitializer {
    private final BookRepository bookRepository;

    private final int INIT_BOOK_AMOUNT = 10;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for(int i = 0; i < INIT_BOOK_AMOUNT; i++) {
            bookRepository.saveBook(
                    BookEntity.builder()
                            .isbn("0-00-" + i)
                            .title("InitBook_" + i)
                            .author("InitAuthor_" + i)
                            .build());
        }
    }
}
