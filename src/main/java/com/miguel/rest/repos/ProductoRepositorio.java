package com.miguel.rest.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.rest.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

    // método para buscar por nombre
    Page<Producto> findByNombreContainsIgnoreCase(String txt, Pageable pageable);
}
