package com.stockbrain.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ventas")
@Data @NoArgsConstructor @AllArgsConstructor
public class EntidadVenta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha = LocalDate.now();
    @PositiveOrZero private int cantidad;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private EntidadProducto producto;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    @JsonIgnore
    private EntidadTienda tienda;
}
