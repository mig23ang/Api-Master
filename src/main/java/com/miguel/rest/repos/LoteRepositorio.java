package com.miguel.rest.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miguel.rest.modelo.Lote;

public interface LoteRepositorio extends JpaRepository<Lote, Long> {

    // @Query("SELECT p from Lote_Producto JOIN FETCH p.productos WHERE p.id = :id")
    // public Optional<Lote> findByIdJoinFetch(Long id);
}
