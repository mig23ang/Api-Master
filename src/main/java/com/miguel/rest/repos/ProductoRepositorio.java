package com.miguel.rest.repos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.miguel.rest.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {

    // método para buscar por nombre
    Page<Producto> findByNombreContainsIgnoreCase(String txt, Pageable pageable);

    @Query("select p from Producto p LEFT JOIN FETCH p.lotes WHERE p.id = :id")
    public Optional<Producto> findByIdJoinFetch(Long id);
}
