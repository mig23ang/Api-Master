package com.miguel.rest.modelo;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	private String nombre;

	private String descripcion;
	private float precio;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

}
