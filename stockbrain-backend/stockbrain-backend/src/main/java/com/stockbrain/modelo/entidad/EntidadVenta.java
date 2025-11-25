package com.stockbrain.modelo.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ventas")
public class EntidadVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @PositiveOrZero
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private EntidadProducto producto;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    private EntidadTienda tienda;
}
