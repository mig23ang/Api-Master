package com.miguel.rest.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miguel.rest.modelo.Lote;

public interface LoteRepositorio extends JpaRepository<Lote, Long> {

    @Query("SELECT l from l JOIN FETCH l.productos WHERE l.id = :id")
    public Optional<Lote> findByIdJoinFetch(Long id);
}
