package com.miguel.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "No hay productos disponibles")
public class ProductEmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProductEmptyException() {
        super("No hay productos disponibles");
    }

    // creando drive de errores para el método obtenerTodos
    @ExceptionHandler({ ProductoNotFoundException.class, ProductEmptyException.class })
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
