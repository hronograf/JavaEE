package com.bigbook.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class WebException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;
}
