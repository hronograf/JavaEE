package com.bigbook.app;


import com.bigbook.app.exceptions.WebException;
import com.bigbook.app.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WebException.class)
    public ResponseEntity<Response<Void>> handleWebException (WebException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleWebException (Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error("Internal server error"));
    }

}
