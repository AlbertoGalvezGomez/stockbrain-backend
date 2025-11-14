package com.stockbrain.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class EntidadProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @PositiveOrZero(message = "El precio no puede ser negativo")
    private double precio;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private int stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tienda_id")
    @JsonBackReference
    private EntidadTienda tienda;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "imagen_ruta")
    private String imagenRuta;  // Ej: "/uploads/productos/123.jpg"

    @JsonProperty("imagenUrl")
    public String getImagenUrl() {
        if (imagenRuta == null || imagenRuta.isEmpty()) return null;
        return "http://192.168.1.133:8080" + imagenRuta;
    }
}
