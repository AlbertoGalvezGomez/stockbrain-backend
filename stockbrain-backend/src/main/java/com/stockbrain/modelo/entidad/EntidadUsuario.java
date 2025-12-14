package com.stockbrain.modelo.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = true,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "tienda_id",
            nullable = true,
            unique = true
    )
    @JsonIgnore
    private EntidadTienda tienda;

    @Transient
    public Long getTiendaId() {
        return tienda != null ? tienda.getId() : null;
    }

    public void setTiendaId(Long tiendaId) {
        if (tiendaId == null) {
            this.tienda = null;
        } else {
            EntidadTienda ref = new EntidadTienda();
            ref.setId(tiendaId);
            this.tienda = ref;
        }
    }
}