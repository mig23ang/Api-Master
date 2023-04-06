package com.miguel.rest.utils.pagination;

import org.springframework.data.domain.Page;

public class PaginationUtils {

    // creamos el método crearPaginationDTO
    public static <T> Pagination<T> crearPaginationDTO(Page<?> page, Page<T> dtoPage) {
        Pagination<T> pagination = new Pagination<>();
        pagination.setTotalPaginas(dtoPage.getTotalPages());
        pagination.setNumPaginaActual(dtoPage.getNumber());
        pagination.setTamPagina(dtoPage.getSize());
        pagination.setTotalElementos(dtoPage.getTotalElements());
        pagination.setElementos(dtoPage.getContent());
        return pagination;
    }

    // método global para pagination sin dto
    public static <T> Pagination<T> crearPagination(Page<?> page) {
        Pagination<T> pagination = new Pagination<>();
        pagination.setTotalPaginas(page.getTotalPages());
        pagination.setNumPaginaActual(page.getNumber());
        pagination.setTamPagina(page.getSize());
        pagination.setTotalElementos(page.getTotalElements());
        return pagination;
    }

}
