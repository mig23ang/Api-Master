package com.miguel.rest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.rest.error.GlobalException;
import com.miguel.rest.modelo.Categoria;
import com.miguel.rest.repos.CategoriaRepositorio;
import com.miguel.rest.services.CategoriaServicio;
import com.miguel.rest.utils.pagination.Pagination;
import com.miguel.rest.utils.pagination.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaRepositorio categoriaRepositorio;

    // inyectamos el servicio de categoria
    private final CategoriaServicio categoriaServicio;

    // obtener todas las categorías
    @GetMapping("/all/")
    public ResponseEntity<Pagination<Categoria>> allCategories(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<Categoria> categoriasPage = categoriaServicio.findAll(pageable);
        List<Categoria> categorias = categoriasPage.getContent();

        if (categorias.isEmpty()) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "No se encontraron categorias");
        }

        Pagination<Categoria> pagination = PaginationUtils.crearPagination(categoriasPage);

        pagination.setElementos(categorias);

        return ResponseEntity.ok().body(pagination);
    }

    // obtener una categoría en base a su ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> categoryById(@PathVariable Long id) {
        return categoriaServicio.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró la categoría"));
    }

    // obtener una categoría en base a su nombre
    // este método se debe agregar al service
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Categoria> categoryByName(@PathVariable String nombre) {
        return categoriaRepositorio.findByNombreMySql(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // crear una categoría
    @PostMapping("/")
    public ResponseEntity<Categoria> createCategory(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaServicio.save(categoria));
    }

    // editar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> editCategory(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaServicio.findById(id)
                .map(c -> {
                    c.setNombre(categoria.getNombre());
                    return ResponseEntity.ok(categoriaServicio.save(c));
                })
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró la categoría"));
    }

    // eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return categoriaServicio.findById(id)
                .map(c -> {
                    categoriaRepositorio.delete(c);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró la categoría"));
    }

}
