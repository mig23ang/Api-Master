package com.miguel.rest.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.miguel.rest.modelo.Lote;

public interface LoteRepositorio extends JpaRepository<Lote, Long> {

    //
    @Query("SELECT DISTINCT l FROM Lote l JOIN FETCH l.productos p WHERE l.id = :id")
    public Optional<Lote> findByIdJoinFetch(Long id);

}
