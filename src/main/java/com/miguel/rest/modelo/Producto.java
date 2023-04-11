package com.miguel.rest.modelo;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

	@Id
	@GeneratedValue
	private Long id;

	private String nombre;

	private String descripcion;
	private Float precio;
	private String imagen;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	// @ManyToMany(mappedBy = "productos", fetch = FetchType.EAGER)
	@ManyToMany(mappedBy = "productos")
	@Builder.Default
	private Set<Lote> lotes = new HashSet<>();

}
