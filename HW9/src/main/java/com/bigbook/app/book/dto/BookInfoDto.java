package com.bigbook.app.book.dto;

import com.bigbook.app.book.BookEntity;
import lombok.Data;


@Data
public class BookInfoDto {
    private String isbn;
    private String title;
    private String author;

    public BookInfoDto(BookEntity bookEntity) {
        this.isbn = bookEntity.getIsbn();
        this.title = bookEntity.getTitle();
        this.author = bookEntity.getAuthor();
    }
}
