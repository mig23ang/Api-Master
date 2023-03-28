package com.miguel.rest.controller;

import java.util.List;

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
import com.miguel.rest.modelo.CategoriaRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaRepositorio categoriaRepositorio;

    // obtener todas las categorías
    @GetMapping("/all")
    public ResponseEntity<List<Categoria>> allCategories() {
        List<Categoria> categorias = categoriaRepositorio.findAll();
        return categorias.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categorias);
    }

    // obtener una categoría en base a su ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> categoryById(@PathVariable Long id) {
        return categoriaRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró la categoría"));
    }

    // obtener una categoría en base a su nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Categoria> categoryByName(@PathVariable String nombre) {
        return categoriaRepositorio.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // crear una categoría
    @PostMapping("/")
    public ResponseEntity<Categoria> createCategory(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaRepositorio.save(categoria));
    }

    // editar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> editCategory(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaRepositorio.findById(id)
                .map(c -> {
                    c.setNombre(categoria.getNombre());
                    return ResponseEntity.ok(categoriaRepositorio.save(c));
                })
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró la categoría"));
    }

    // eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return categoriaRepositorio.findById(id)
                .map(c -> {
                    categoriaRepositorio.delete(c);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró la categoría"));
    }

}
