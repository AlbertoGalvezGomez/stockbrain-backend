package com.stockbrain.controlador;

import com.stockbrain.modelo.entidad.EntidadUsuario;
import com.stockbrain.modelo.login.LoginRequest;
import com.stockbrain.modelo.login.LoginResponse;
import com.stockbrain.servicio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://192.168.1.133:8080/")
public class AuthController {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Recibido: email=" + loginRequest.getEmail() + ", password=" + loginRequest.getPassword());
        EntidadUsuario usuario = servicioUsuario.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (usuario != null) {
            LoginResponse response = new LoginResponse();
            response.setId(usuario.getId());
            response.setEmail(usuario.getEmail());
            response.setRol(usuario.getRol() != null ? usuario.getRol().name() : "USER");
            response.setMessage("Login exitoso");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(new LoginResponse(null, null, null, "Credenciales inválidas"));
    }
}
