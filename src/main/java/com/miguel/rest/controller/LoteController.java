package com.miguel.rest.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.miguel.rest.dto.CreateLoteDTO;
import com.miguel.rest.error.GlobalException;
import com.miguel.rest.modelo.Lote;
import com.miguel.rest.services.LoteService;
import com.miguel.rest.utils.pagination.Pagination;
import com.miguel.rest.utils.pagination.PaginationLinksUtils;
import com.miguel.rest.utils.pagination.PaginationUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/lotes")
@RequiredArgsConstructor
public class LoteController {

	private final LoteService loteServicio;
	private final PaginationLinksUtils paginationLinksUtils;

	@GetMapping("/")
	public ResponseEntity<?> lotes(Pageable pageable, HttpServletRequest request) {
		Page<Lote> result = loteServicio.findAll(pageable);
		List<Lote> lotes = result.getContent();
		if (result.isEmpty()) {
			throw new GlobalException(HttpStatus.NOT_FOUND, "No se encontraron categorias");
		}

		Pagination<Lote> pagination = PaginationUtils.crearPagination(result);

		pagination.setElementos(lotes);

		return ResponseEntity.ok().body(pagination);

	}

	@PostMapping("/")
	public ResponseEntity<?> nuevoLote(@RequestBody CreateLoteDTO nuevoLote) {
		Lote lote = loteServicio.nuevoLote(nuevoLote);

		return ResponseEntity.status(HttpStatus.CREATED).body(lote);
	}

}
