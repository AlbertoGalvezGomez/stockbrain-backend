package com.stockbrain.controlador;

import com.stockbrain.modelo.dao.IUsuarioDAO;
import com.stockbrain.modelo.entidad.EntidadUsuario;
import com.stockbrain.servicio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://192.168.1.20/")
public class ControladorUsuario {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping
    public List<EntidadUsuario> listarUsuarios() {
        return servicioUsuario.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadUsuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return servicioUsuario.buscarUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntidadUsuario> crearUsuario(@RequestBody EntidadUsuario usuario) {
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());

        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            String hashed = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(hashed);
        }

        return ResponseEntity.ok(usuarioDAO.save(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (servicioUsuario.buscarUsuarioPorId(id).isPresent()) {
            servicioUsuario.eliminarUsuarioPorId(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntidadUsuario> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody EntidadUsuario usuarioActualizado) {

        System.out.println("ID: " + id);
        System.out.println("Datos recibidos: " + usuarioActualizado);

        return servicioUsuario.actualizarUsuario(id, usuarioActualizado)
                .map(usuarioGuardado -> {
                    System.out.println("Usuario actualizado con éxito: ID=" + usuarioGuardado.getId());
                    return ResponseEntity.ok(usuarioGuardado);
                })
                .orElseGet(() -> {
                    System.out.println("Usuario NO encontrado: ID=" + id);
                    return ResponseEntity.notFound().build();
                });
    }
}