package com.miguel.rest.services;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.miguel.rest.dto.CreateLoteDTO;
import com.miguel.rest.modelo.Lote;
import com.miguel.rest.repos.LoteRepositorio;
import com.miguel.rest.services.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoteService extends BaseService<Lote, Long, LoteRepositorio> {

    private final ProductoServicio productoServicio;

    @Override
    public Optional<Lote> findById(Long id) {
        return repositorio.findByIdJoinFetch(id);
    }

    public Lote nuevoLote(CreateLoteDTO nuevoLote) {
        Lote l = Lote.builder()
                .nombre(nuevoLote.getNombre())
                .build();
        nuevoLote.getProductos().forEach(p -> l.addProducto(productoServicio.findById(p).orElseThrow()));
        return repositorio.save(l);
    }

}
