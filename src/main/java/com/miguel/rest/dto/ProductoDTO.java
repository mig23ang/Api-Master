package com.miguel.rest.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.miguel.rest.dto.views.ProductoViews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {

    @JsonView(ProductoViews.Dto.class)
    private long id;

    @JsonView(ProductoViews.Dto.class)
    private String nombre;

    @JsonView(ProductoViews.Dto.class)
    private String descripcion;

    @JsonView(ProductoViews.Dto.class)
    private String imagen;

    @JsonView(ProductoViews.DtoConPrecio.class)
    private float precio;

    @JsonView(ProductoViews.Dto.class)
    private String categoriaNombre;

}
