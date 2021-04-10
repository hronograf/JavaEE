package com.bigbook.app.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends WebException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, "BAD REQUEST");
    }
}
