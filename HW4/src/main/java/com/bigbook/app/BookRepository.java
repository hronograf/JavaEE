package com.bigbook.app;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
public class BookRepository {

    private List<BookModel> books = new ArrayList<>();

    {
        books.add(new BookModel("InitBook", "0-00-000000-0", "InitAuthor"));
    }

    public void saveBook(BookModel book) {
        books.add(book);
    }
}
