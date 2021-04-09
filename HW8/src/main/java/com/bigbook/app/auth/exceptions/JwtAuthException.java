package com.bigbook.app.auth.exceptions;

import com.bigbook.app.exceptions.WebException;
import org.springframework.http.HttpStatus;


public class JwtAuthException extends WebException {

    public JwtAuthException() {
        super(HttpStatus.UNAUTHORIZED, "JWT token is expired or invalid");
    }

}
