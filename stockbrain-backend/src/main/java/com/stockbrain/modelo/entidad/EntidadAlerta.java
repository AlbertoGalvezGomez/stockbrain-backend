package com.stockbrain.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alertas")
@Data @NoArgsConstructor @AllArgsConstructor
public class EntidadAlerta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String tipo;
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private EntidadProducto producto;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    @JsonIgnore
    private EntidadTienda tienda;
}
