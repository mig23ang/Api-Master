package com.miguel.rest.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.miguel.rest.controller.FicherosController;
import com.miguel.rest.dto.CreateProductoDTO;
import com.miguel.rest.modelo.Producto;
import com.miguel.rest.repos.ProductoRepositorio;
import com.miguel.rest.services.base.BaseService;
import com.miguel.rest.upload.StorageService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServicio extends BaseService<Producto, Long, ProductoRepositorio> {

	private final CategoriaServicio categoriaServicio;
	private final StorageService storageService;

	public Producto nuevoProducto(CreateProductoDTO nuevo, MultipartFile file) {
		String urlImagen = null;

		if (!file.isEmpty()) {
			String imagen = storageService.store(file);
			urlImagen = MvcUriComponentsBuilder
					.fromMethodName(FicherosController.class, "serveFile", imagen, null)
					.build().toUriString();
		}
		/**
		 * En ocasiones, no necesitamos el uso de ModelMapper si la conversión que vamos
		 * a hacer es muy sencilla. Con el uso de @Builder sobre la clase en cuestión,
		 * podemos realizar una
		 * transformación rápida como esta.
		 */
		Producto nuevoProducto = Producto.builder()
				.nombre(nuevo.getNombre())
				.precio(nuevo.getPrecio())
				.imagen(urlImagen)
				.categoria(categoriaServicio.findById(nuevo.getCategoriaId()).orElse(null))
				.build();

		return this.save(nuevoProducto);
	}

	// método para buscar por nombre
	public Page<Producto> findByNombre(String txt, Pageable pageable) {
		return this.repositorio.findByNombreContainsIgnoreCase(txt, pageable);
	}

	// método para buscar por Args
	public Page<Producto> findByArgs(final Optional<String> nombre, final Optional<Float> precio, Pageable pageable) {

		Specification<Producto> specNombreProducto = new Specification<Producto>() {
			@Override
			public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (nombre.isPresent()) {
					return criteriaBuilder.like(root.get("nombre"), "%" + nombre.get() + "%");
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}
		};

		Specification<Producto> specPrecioMenorQue = new Specification<Producto>() {
			@Override
			public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (precio.isPresent()) {
					return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precio.get());
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}

			}
		};

		Specification<Producto> specs = specNombreProducto.and(specPrecioMenorQue);

		return this.repositorio.findAll(specs, pageable);
	}

}
