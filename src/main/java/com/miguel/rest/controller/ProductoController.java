package com.miguel.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.rest.modelo.Producto;
import com.miguel.rest.modelo.ProductoRepositorio;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductoController {

	private final ProductoRepositorio productoRepositorio;

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */
	@GetMapping("/producto/all")
	public ResponseEntity<List<Producto>> obtenerTodos() {
		List<Producto> productos = productoRepositorio.findAll();
		return productos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(productos);
	}

	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	@GetMapping("/producto/{id}")
	public ResponseEntity<Producto> obtenerUno(@PathVariable Long id) {
		return productoRepositorio.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Insertamos un nuevo producto
	 * 
	 * @param nuevo
	 * @return producto insertado
	 */
	@PostMapping("/producto")
	public ResponseEntity<Producto> nuevoProducto(@Valid @RequestBody Producto nuevo) {
		Producto result = productoRepositorio.save(nuevo);
		return ResponseEntity.created(null).body(result);
	}

	/**
	 * 
	 * @param editar
	 * @param id
	 * @return
	 */
	@PutMapping("/producto/{id}")
	public ResponseEntity<Producto> editarProducto(@Valid @RequestBody Producto editar, @PathVariable Long id) {
		Optional<Producto> optionalProducto = productoRepositorio.findById(id);
		if (optionalProducto.isPresent()) {
			editar.setId(id);
			Producto result = productoRepositorio.save(editar);
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Borra un producto del catálogo en base a su id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<Void> borrarProducto(@PathVariable Long id) {
		Optional<Producto> optionalProducto = productoRepositorio.findById(id);
		if (optionalProducto.isPresent()) {
			productoRepositorio.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
