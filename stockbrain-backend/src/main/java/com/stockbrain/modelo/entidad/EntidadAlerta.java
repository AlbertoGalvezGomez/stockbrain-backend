package com.stockbrain.modelo.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alertas")
public class EntidadAlerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo; // STOCK_BAJO, VENTA_ALTA, etc.
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private EntidadProducto producto;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private EntidadTienda tienda;
}
