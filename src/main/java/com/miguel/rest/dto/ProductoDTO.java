package com.miguel.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductoDTO {
    
    private String nombre;
    private float precio;
    private String categoriaNombre;
}
