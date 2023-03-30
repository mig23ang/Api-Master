package com.miguel.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.miguel.rest.dto.CreateProductoDTO;
import com.miguel.rest.dto.EditarProductoDTO;
import com.miguel.rest.dto.ProductoDTO;
import com.miguel.rest.dto.converter.ProductoDTOConverter;
import com.miguel.rest.error.GlobalException;
import com.miguel.rest.modelo.Categoria;
import com.miguel.rest.modelo.Producto;
import com.miguel.rest.repos.CategoriaRepositorio;
import com.miguel.rest.repos.ProductoRepositorio;
import com.miguel.rest.upload.StorageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producto")
public class ProductoController {

	private final ProductoRepositorio productoRepositorio;
	// añadimos el converter para que sepa como convertir los datos de la petición
	private final ProductoDTOConverter productoDTOConverter;
	private final CategoriaRepositorio categoriaRepositorio;

	// Inyectamos StorageService
	private final StorageService storageService;

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */

	@GetMapping("/all")
	public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
		List<Producto> productos = productoRepositorio.findAll();
		List<ProductoDTO> dtoList = productos.stream()
				.map(productoDTOConverter::convertToDto)
				.collect(Collectors.toList());
		if (dtoList.isEmpty()) {
			throw new GlobalException(HttpStatus.NOT_FOUND, "No se encontraron productos");
		}
		return ResponseEntity.ok(dtoList);
	}

	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Producto> obtenerUno(@PathVariable Long id) {
		return productoRepositorio.findById(id)
				.map(ResponseEntity::ok)
				.orElseThrow(
						() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró el producto con id " + id));
	}

	/**
	 * Insertamos un nuevo producto
	 * 
	 * @param nuevo
	 * @return producto insertado
	 */
	@PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ProductoDTO> nuevoProducto(@Valid @RequestPart("nuevo") CreateProductoDTO nuevo,
			@RequestPart("file") MultipartFile file) {
		String urlImage = null;
		if (file != null) {
			String image = storageService.store(file);
			urlImage = MvcUriComponentsBuilder
					.fromMethodName(FicherosController.class, "serveFile", image, null)
					.build()
					.toString();
		}
		Categoria categoria = categoriaRepositorio.findById(nuevo.getCategoriaId())
				.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND,
						"No se encontró la categoría con id " + nuevo.getCategoriaId()));

		try {
			Producto nuevoP = new Producto();
			nuevoP.setNombre(nuevo.getNombre());
			nuevoP.setDescripcion(nuevo.getDescripcion());
			nuevoP.setImagen(urlImage);
			nuevoP.setPrecio(nuevo.getPrecio());
			nuevoP.setCategoria(categoria);

			Producto result = productoRepositorio.save(nuevoP);

			ProductoDTO resultDTO = productoDTOConverter.convertToDto(result);

			return ResponseEntity.created(null).body(resultDTO);
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	/**
	 * 
	 * @param editar
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ProductoDTO> editarProducto(@Valid @RequestBody EditarProductoDTO editar,
			@PathVariable Long id) {
		Optional<Producto> optionalProducto = productoRepositorio.findById(id);
		Categoria categoria = categoriaRepositorio.findById(editar.getCategoriaId())
				.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND,
						"No se encontró la categoría con id " + editar.getCategoriaId()));

		return optionalProducto.map(producto -> {
			producto.setNombre(editar.getNombre());
			producto.setDescripcion(editar.getDescripcion());
			producto.setPrecio(editar.getPrecio());
			producto.setCategoria(categoria);
			Producto result = productoRepositorio.save(producto);
			ProductoDTO resultDTO = productoDTOConverter.convertToDto(result);
			return ResponseEntity.ok(resultDTO);
		}).orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró el producto con id " + id));
	}

	/**
	 * Borra un producto del catálogo en base a su id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> borrarProducto(@PathVariable Long id) {
		return productoRepositorio.findById(id)
				.map(c -> {
					productoRepositorio.delete(c);
					return ResponseEntity.noContent().<Void>build();
				})
				.orElseThrow(
						() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró el producto con id " + id));
	}

}
