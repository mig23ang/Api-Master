package com.miguel.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "No hay productos disponibles")
public class ProductEmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProductEmptyException() {
        super("No hay productos disponibles");
    }

}
