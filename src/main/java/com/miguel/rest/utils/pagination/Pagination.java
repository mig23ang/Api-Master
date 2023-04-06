package com.miguel.rest.utils.pagination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {

    private int totalPaginas;
    private int numPaginaActual;
    private int tamPagina;
    private long totalElementos;
    private List<T> elementos;

}
