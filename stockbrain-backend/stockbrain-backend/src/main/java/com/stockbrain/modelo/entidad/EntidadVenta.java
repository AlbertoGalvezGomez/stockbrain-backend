package com.stockbrain.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private EntidadProducto producto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private EntidadUsuario usuario;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private EntidadTienda tienda;
}
