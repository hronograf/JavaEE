package com.bigbook.app.book.exceptions;

import com.bigbook.app.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class BookAlreadyExistsException extends WebException {
    public BookAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Book with this isbn has already been added");
    }
}
