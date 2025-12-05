package com.stockbrain.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data @NoArgsConstructor @AllArgsConstructor
public class EntidadProducto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String nombre;
    private double precio;
    private int stock;
    private String descripcion;
    private String imagenRuta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tienda_id", nullable = false)
    @JsonIgnore
    private EntidadTienda tienda;

    @JsonProperty("imagenUrl")
    public String getImagenUrl() {
        if (imagenRuta == null) return null;
        String baseUrl = "http://10.0.2.2:8080";
        return baseUrl + imagenRuta;
    }
}
