package com.bigbook.app.auth.exceptions;

import com.bigbook.app.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class UsernameExistsException extends WebException {

    public UsernameExistsException() {
        super(HttpStatus.BAD_REQUEST, "User with such username already exists");
    }

}
