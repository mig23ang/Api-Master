package com.miguel.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {

    private String nombre;
    private String descripcion;
    private String imagen;
    private float precio;
    private String categoriaNombre;

}
