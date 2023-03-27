package com.miguel.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    // productos
    @ExceptionHandler(value = { ProductoNotFoundException.class, CategoriaNotFoundException.class })
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductEmptyException.class)
    public ResponseEntity<String> handleProductEmptyException(ProductEmptyException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }

    // categorias
    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(CategoriaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }
}
