package com.miguel.rest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.rest.error.GlobalException;
import com.miguel.rest.modelo.Pedido;
import com.miguel.rest.services.PedidoService;
import com.miguel.rest.utils.pagination.Pagination;
import com.miguel.rest.utils.pagination.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoServicio;

    @GetMapping("/")
    public ResponseEntity<Pagination<Pedido>> allCategories(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<Pedido> pedidosPage = pedidoServicio.findAll(pageable);
        List<Pedido> Pedidos = pedidosPage.getContent();

        if (Pedidos.isEmpty()) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "No se encontraron Pedidos");
        }

        Pagination<Pedido> pagination = PaginationUtils.crearPagination(pedidosPage);

        pagination.setElementos(Pedidos);

        return ResponseEntity.ok().body(pagination);

    }
}
