package com.miguel.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductoDTO {

    private String nombre;
    private String descripcion;
    private float precio;
    private long categoriaId;
}
