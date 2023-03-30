package com.miguel.rest.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.rest.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

}
