package com.miguel.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.rest.dto.CreateProductoDTO;
import com.miguel.rest.dto.ProductoDTO;
import com.miguel.rest.dto.converter.ProductoDTOConverter;
import com.miguel.rest.modelo.Categoria;
import com.miguel.rest.modelo.CategoriaRepositorio;
import com.miguel.rest.modelo.Producto;
import com.miguel.rest.modelo.ProductoRepositorio;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductoController {

	private final ProductoRepositorio productoRepositorio;
	// añadimos el converter para que sepa como convertir los datos de la petición
	private final ProductoDTOConverter productoDTOConverter;
	private final CategoriaRepositorio categoriaRepositorio;

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */
	@GetMapping("/producto/all")
	public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
		List<Producto> productos = productoRepositorio.findAll();
		// con estas lineas mostramos el objeto completo anidado
		// return productos.isEmpty() ? ResponseEntity.notFound().build() :
		// ResponseEntity.ok(productos);
		List<ProductoDTO> dtoList = productos.stream()
				.map(productoDTOConverter::convertToDto)
				.collect(Collectors.toList());
		return dtoList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dtoList);
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
	public ResponseEntity<ProductoDTO> nuevoProducto(@Valid @RequestBody CreateProductoDTO nuevo) {
		Categoria categoria = categoriaRepositorio.findById(nuevo.getCategoriaId())
				.orElseThrow(() -> new RuntimeException(
						"No se encontró la categoría con el id " + nuevo.getCategoriaId()));

		Producto nuevoP = new Producto();
		nuevoP.setNombre(nuevo.getNombre());
		nuevoP.setDescripcion(nuevo.getDescripcion());
		nuevoP.setPrecio(nuevo.getPrecio());
		nuevoP.setCategoria(categoria);

		Producto result = productoRepositorio.save(nuevoP);

		ProductoDTO resultDTO = productoDTOConverter.convertToDto(result);

		return ResponseEntity.created(null).body(resultDTO);
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
