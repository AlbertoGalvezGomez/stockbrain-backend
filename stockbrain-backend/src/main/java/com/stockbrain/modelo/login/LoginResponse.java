package com.stockbrain.modelo.login;

import com.stockbrain.modelo.entidad.EntidadUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
    private Long tiendaId;
    private String message;

    public LoginResponse(EntidadUsuario usuario, String message) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.rol = usuario.getRol().name();
        this.tiendaId = usuario.getTiendaId();
        this.message = message;
    }
}