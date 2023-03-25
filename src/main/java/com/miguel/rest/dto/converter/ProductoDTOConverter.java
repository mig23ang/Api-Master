package com.miguel.rest.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.miguel.rest.dto.ProductoDTO;
import com.miguel.rest.modelo.Producto;

@Component
public class ProductoDTOConverter {

    //método moderno con inyección de dependencias
    private final ModelMapper modelMapper;

    public ProductoDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductoDTO convertToDto(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }
}

