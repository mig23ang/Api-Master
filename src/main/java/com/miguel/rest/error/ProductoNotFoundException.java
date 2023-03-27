package com.miguel.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "El producto no ha sido encontrado")
public class ProductoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public ProductoNotFoundException(Long id) {
        super("No se ha encontrado el producto: " + id);
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ProductoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }
}
