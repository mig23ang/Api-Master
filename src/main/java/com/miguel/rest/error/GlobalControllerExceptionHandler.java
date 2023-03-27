package com.miguel.rest.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

}
