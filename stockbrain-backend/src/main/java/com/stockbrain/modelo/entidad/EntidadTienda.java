package com.stockbrain.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tiendas")
public class EntidadTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    // RELACIÓN CORRECTA: lado inverso del @OneToOne que tienes en Usuario
    // La FK está en la tabla usuarios (columna tienda_id) → por eso usamos "mappedBy"
    @OneToOne(mappedBy = "tienda", fetch = FetchType.LAZY)
    @JsonIgnore
    private EntidadUsuario administrador;

    @OneToMany(
            mappedBy = "tienda",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<EntidadProducto> productos = new ArrayList<>();

    @OneToMany(
            mappedBy = "tienda",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<EntidadVenta> ventas = new ArrayList<>();

    @OneToMany(
            mappedBy = "tienda",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<EntidadAlerta> alertas = new ArrayList<>();
}