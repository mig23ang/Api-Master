package com.miguel.rest.services;

import org.springframework.stereotype.Service;

import com.miguel.rest.modelo.Pedido;
import com.miguel.rest.repos.PedidoRepositorio;
import com.miguel.rest.services.base.BaseService;

@Service
public class PedidoService extends BaseService<Pedido, Long, PedidoRepositorio> {

}
