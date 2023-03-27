package com.miguel.rest.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    // @Override
    // protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
    //         HttpStatus status, WebRequest request) {
    //     HttpHeaders httpHeaders = new HttpHeaders();
    //     ErrorResponse apiError = new ErrorResponse(status, ex.getMessage());
    //     return ResponseEntity.status(status).headers(httpHeaders).body(apiError);
    // }

}
