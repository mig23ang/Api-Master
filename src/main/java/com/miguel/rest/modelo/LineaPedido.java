package com.miguel.rest.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "linea_pedido")
public class LineaPedido {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private float precio;
    private Integer cantidad;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public float getSubtotal() {
        return precio * cantidad;
    }
}
