package com.bigbook.app.book.dto;

import com.bigbook.app.book.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SearchBookResponseDto {
    private List<BookEntity> books;
    private int canBeShown;
}
