package com.stockbrain.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private EntidadUsuario administrador;

    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EntidadUsuario> empleados;

    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EntidadProducto> productos;
}
