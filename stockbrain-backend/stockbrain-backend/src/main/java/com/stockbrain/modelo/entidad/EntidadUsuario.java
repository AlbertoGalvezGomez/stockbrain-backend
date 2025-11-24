package com.stockbrain.modelo.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class EntidadUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = true)
    @JsonBackReference
    private EntidadTienda tienda;

    public Long getTiendaId() {
        return tienda != null ? tienda.getId() : null;
    }

    public void setTiendaId(Long tiendaId) {
        if (tiendaId == null) {
            this.tienda = null;
        } else {
            this.tienda = new EntidadTienda();
            this.tienda.setId(tiendaId);
        }
    }
}