package com.miguel.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.miguel.rest.dto.CreateProductoDTO;
import com.miguel.rest.dto.EditarProductoDTO;
import com.miguel.rest.dto.ProductoDTO;
import com.miguel.rest.dto.converter.ProductoDTOConverter;
import com.miguel.rest.dto.views.ProductoViews;
import com.miguel.rest.error.GlobalException;
import com.miguel.rest.modelo.Categoria;
import com.miguel.rest.modelo.Producto;
import com.miguel.rest.repos.CategoriaRepositorio;
import com.miguel.rest.repos.ProductoRepositorio;
import com.miguel.rest.services.ProductoServicio;
import com.miguel.rest.upload.StorageService;
import com.miguel.rest.utils.pagination.Pagination;
import com.miguel.rest.utils.pagination.PaginationLinksUtils;
import com.miguel.rest.utils.pagination.PaginationUtils;

import jakarta.servlet.http.HttpServletRequest;
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

	// inyectamos el servicio de producto
	private final ProductoServicio productoServicio;

	// Inyectamos la Pagination
	private final PaginationLinksUtils paginationLinksUtils;

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */

	// esta anotación en Jakarta ya no es necesaria ya que lista el precio en el
	// dto. pero si usa java 8 o 11, es necesario
	@JsonView(ProductoViews.DtoConPrecio.class)
	@GetMapping("/all/")
	public ResponseEntity<Pagination<ProductoDTO>> obtenerTodos(@PageableDefault(size = 10, page = 0) Pageable pageable,
			HttpServletRequest request) {
		Page<Producto> productos = productoServicio.findAll(pageable);

		if (productos.isEmpty()) {
			throw new GlobalException(HttpStatus.NOT_FOUND, "No se encontraron productos");
		}
		// traemos el método crearPaginationDTO que es un método global que nos va a
		// servir para cualquier pagination con dto
		Page<ProductoDTO> dtoPage = productos
				.map(productoDTOConverter::converterProductoToProductoDTO);
		Pagination<ProductoDTO> pagination = PaginationUtils.crearPaginationDTO(productos, dtoPage);

		return ResponseEntity.ok().body(pagination);
	}

	/**
	 * Obtenemos todos los productos por Args
	 * 
	 * @param txt @param precio
	 * @return los productos que tengan ese texto y un precio menor o igual al
	 *         entregado en el parámetro
	 */
	@GetMapping("/args/")
	public ResponseEntity<?> buscarPorSpecs(
			@RequestParam("nombre") Optional<String> txt,
			@RequestParam("precio") Optional<Float> precio,
			Pageable pageable, HttpServletRequest request) {
		Page<Producto> productos = productoServicio.findByArgs(txt, precio, pageable);

		if (productos.isEmpty()) {
			throw new GlobalException(HttpStatus.NOT_FOUND, "No se encontraron productos con ese nombre y precio");
		}

		Page<ProductoDTO> dtoPage = productos.map(productoDTOConverter::convertToDto);
		Pagination<ProductoDTO> pagination = PaginationUtils.crearPaginationDTO(productos, dtoPage);

		return ResponseEntity.ok().body(pagination);
	}

	/**
	 * obtener producto por texto
	 * 
	 * @param txt
	 * @return Productos filtrados por texto
	 */

	@GetMapping("/")
	public ResponseEntity<?> obtenerPorNombre(@RequestParam("nombre") String nombre, Pageable pageable,
			HttpServletRequest request) {
		Page<Producto> productos = productoServicio.findByNombre(nombre, pageable);

		if (productos.isEmpty()) {
			throw new GlobalException(HttpStatus.NOT_FOUND, "No se encontraron productos con ese nombre");
		}

		Page<ProductoDTO> dtoPage = productos.map(productoDTOConverter::convertToDto);
		Pagination<ProductoDTO> pagination = PaginationUtils.crearPaginationDTO(productos, dtoPage);

		return ResponseEntity.ok().body(pagination);
	}

	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Producto> obtenerUno(@PathVariable Long id) {
		return productoServicio.findById(id)
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

			Producto result = productoServicio.save(nuevoP);

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
		Optional<Producto> optionalProducto = productoServicio.findById(id);
		Categoria categoria = categoriaRepositorio.findById(editar.getCategoriaId())
				.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND,
						"No se encontró la categoría con id " + editar.getCategoriaId()));

		return optionalProducto.map(producto -> {
			producto.setNombre(editar.getNombre());
			producto.setDescripcion(editar.getDescripcion());
			producto.setPrecio(editar.getPrecio());
			producto.setCategoria(categoria);
			Producto result = productoServicio.save(producto);
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
		return productoServicio.findById(id)
				.map(c -> {
					productoServicio.delete(c);
					return ResponseEntity.noContent().<Void>build();
				})
				.orElseThrow(
						() -> new GlobalException(HttpStatus.NOT_FOUND, "No se encontró el producto con id " + id));
	}

}
