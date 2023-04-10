package com.miguel.rest.modelo;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue
    private Long id;

    private String cliente;

    @CreatedDate
    private LocalDateTime fecha;

    // agregamos la bi direccionalidad con LineaPedido
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LineaPedido> lineas = new HashSet<>();

    // creamos un método que nos traiga el total del pedido
    public float getTotal() {
        return (float) lineas.stream().mapToDouble(LineaPedido::getSubtotal).sum();
    }

    //métodos helper para agregar y eliminar lineas de pedido
    //permiten que sea bi direccional
    public void addLineaPedido(LineaPedido linea) {
        lineas.add(linea);
        linea.setPedido(this);
    }

    public void removeLineaPedido(LineaPedido linea) {
        lineas.remove(linea);
        linea.setPedido(null);
    }

}