package com.miguel.rest.modelo;



import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

	@Id @GeneratedValue
	private Long id;
	
	private String nombre;
	
}
