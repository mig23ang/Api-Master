package com.miguel.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "La categoría no ha sido encontrada")
public class CategoriaNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoriaNotFoundException(Long id) {
        super("No se ha encontrado la categoría con el id: " + id);
    }
}
