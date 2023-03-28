package com.miguel.rest.modelo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    //método para buscar una categoria por su nombre
    Optional<Categoria> findByNombre(@Param("nombre") String nombre);
}
