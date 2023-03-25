package com.miguel.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {

    private String nombre;
    private String descripcion;
    private float precio;
    private String categoriaNombre;

}
