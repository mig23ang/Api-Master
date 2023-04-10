package com.miguel.rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.rest.modelo.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {

}
