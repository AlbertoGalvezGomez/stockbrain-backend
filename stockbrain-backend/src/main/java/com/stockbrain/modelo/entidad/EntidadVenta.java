package com.stockbrain.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "ventas")
@Data @NoArgsConstructor @AllArgsConstructor
public class EntidadVenta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha = LocalDate.now();

    @PositiveOrZero private int cantidad;

    @ManyToOne(optional = true)
    @JoinColumn(name = "producto_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EntidadProducto producto;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    @JsonIgnore
    private EntidadTienda tienda;
}
