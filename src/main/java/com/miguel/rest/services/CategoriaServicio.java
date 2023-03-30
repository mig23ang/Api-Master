package com.miguel.rest.services;

import org.springframework.stereotype.Service;

import com.miguel.rest.modelo.Categoria;
import com.miguel.rest.repos.CategoriaRepositorio;
import com.miguel.rest.services.base.BaseService;

@Service
public class CategoriaServicio extends BaseService<Categoria, Long, CategoriaRepositorio> {

}
