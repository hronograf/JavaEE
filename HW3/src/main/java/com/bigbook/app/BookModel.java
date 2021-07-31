package com.bigbook.app;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class BookModel {
    private final String title;
    private final String isbn;
    private final String author;
}
