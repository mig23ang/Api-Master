package com.miguel.rest.modelo;

import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Lote.class)
public class Lote {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @ManyToMany
    @JoinTable(name = "lote_producto", joinColumns = @JoinColumn(name = "lote_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
    @Builder.Default
    private Set<Producto> productos = new HashSet<>();

    // métodos helper
    public void addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getLotes().add(this);
    }

    public void removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getLotes().remove(this);
    }

}
