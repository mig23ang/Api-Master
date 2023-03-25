package com.miguel.rest.modelo;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Producto {

	@Id @GeneratedValue
	private Long id;
	
	private String nombre;
	
	private float precio;
	
}
