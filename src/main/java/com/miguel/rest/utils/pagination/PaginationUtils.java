package com.miguel.rest.utils.pagination;

import java.util.List;

import org.springframework.data.domain.Page;

public class PaginationUtils {

    // creamos el método crearPaginationDTO
    public static <T> Pagination<T> crearPaginationDTO(Page<?> page, List<T> dtoList) {
        Pagination<T> pagination = new Pagination<>();
        pagination.setTotalPaginas(page.getTotalPages());
        pagination.setNumPaginaActual(page.getNumber());
        pagination.setTamPagina(page.getSize());
        pagination.setTotalElementos(page.getTotalElements());
        pagination.setElementos(dtoList);
        return pagination;
    }

    //método global para pagination sin dto
    public static <T> Pagination<T> crearPagination(Page<?> page) {
        Pagination<T> pagination = new Pagination<>();
        pagination.setTotalPaginas(page.getTotalPages());
        pagination.setNumPaginaActual(page.getNumber());
        pagination.setTamPagina(page.getSize());
        pagination.setTotalElementos(page.getTotalElements());
        return pagination;
    }

}
