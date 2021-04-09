package com.bigbook.app.auth.exceptions;

import com.bigbook.app.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class InvalidUsernameOrPasswordException extends WebException {

    public InvalidUsernameOrPasswordException() {
        super(HttpStatus.BAD_REQUEST, "Invalid username or password");
    }

}
