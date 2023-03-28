package com.miguel.rest.error;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException {
    private HttpStatus status;

    public GlobalException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
