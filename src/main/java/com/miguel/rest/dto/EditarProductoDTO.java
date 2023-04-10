package com.miguel.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditarProductoDTO {
    
    private String nombre;
    private String descripcion;
    private String imagen;
    private float precio;
    private long categoriaId;
}
