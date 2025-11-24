package com.stockbrain.servicio;


import com.stockbrain.modelo.dao.IUsuarioDAO;
import com.stockbrain.modelo.entidad.EntidadUsuario;
import com.stockbrain.seguridad.SecurityConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<EntidadUsuario> listarUsuarios() {
        return usuarioDAO.findAll();
    }

    public Optional<EntidadUsuario> buscarUsuarioPorId(Long id) {
        return usuarioDAO.findById(id);
    }

    public EntidadUsuario guardarUsuario(EntidadUsuario usuario) {
        return usuarioDAO.save(usuario);
    }

    private void hashearSiEsNecesario(EntidadUsuario usuario, String passwordPlana) {
        if (passwordPlana != null && !passwordPlana.isEmpty() && !passwordPlana.startsWith("$2a$")) {
            String hashed = passwordEncoder.encode(passwordPlana);
            usuario.setPassword(hashed);
            System.out.println("Contraseña hasheada (nueva o texto plano)");
        } else if (passwordPlana != null && passwordPlana.startsWith("$2a$")) {
            usuario.setPassword(passwordPlana); // Ya está hasheada
            System.out.println("Contraseña ya hasheada, se mantiene");
        }
    }

    private boolean verificarPassword(String passwordPlana, EntidadUsuario usuario) {
        System.out.println("=== VERIFICACIÓN DE CONTRASEÑA ===");
        System.out.println("Password recibida (plana): '" + passwordPlana + "'");
        System.out.println("Password en DB (hasheada): '" + usuario.getPassword() + "'");

        boolean resultado = passwordEncoder.matches(passwordPlana, usuario.getPassword());
        System.out.println("¿Coincide? " + resultado);

        return resultado;
    }

    public void eliminarUsuarioPorId(Long id) {
        usuarioDAO.deleteById(id);
    }

    public EntidadUsuario authenticate(String email, String password) {
        if (email == null) {
            System.out.println("Email es null");
            return null;
        }
        String emailNormalizado = email.trim().toLowerCase();

        Optional<EntidadUsuario> usuarioOpt = usuarioDAO.findByEmail(emailNormalizado);

        if (usuarioOpt.isEmpty()) {
            System.out.println("USUARIO NO ENCONTRADO para email: '" + emailNormalizado + "'");
            return null;
        }

        EntidadUsuario usuario = usuarioOpt.get();
        System.out.println("USUARIO ENCONTRADO:");
        System.out.println("  ID: " + usuario.getId());
        System.out.println("  Email en DB: '" + usuario.getEmail() + "'");
        System.out.println("  Rol: " + usuario.getRol());

        if (!verificarPassword(password, usuario)) {
            System.out.println("CONTRASEÑA INCORRECTA para email: '" + emailNormalizado + "'");
            return null;
        }

        System.out.println("AUTENTICACIÓN EXITOSA para email: '" + emailNormalizado + "'");
        return usuario;
    }

    public Optional<EntidadUsuario> actualizarUsuario(Long id, EntidadUsuario usuarioActualizado) {
        return usuarioDAO.findById(id).map(usuarioExistente -> {

            if (usuarioActualizado.getNombre() != null && !usuarioActualizado.getNombre().trim().isEmpty()) {
                usuarioExistente.setNombre(usuarioActualizado.getNombre().trim());
            }

            if (usuarioActualizado.getEmail() != null && !usuarioActualizado.getEmail().trim().isEmpty()) {
                String emailNuevo = usuarioActualizado.getEmail().trim().toLowerCase();
                if (!emailNuevo.equals(usuarioExistente.getEmail()) &&
                        usuarioDAO.findByEmail(emailNuevo).isPresent()) {
                    throw new IllegalArgumentException("Email ya en uso");
                }
                usuarioExistente.setEmail(emailNuevo);
            }

            if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().trim().isEmpty()) {
                hashearSiEsNecesario(usuarioExistente, usuarioActualizado.getPassword().trim());
            }

            if (usuarioActualizado.getRol() != null) {
                usuarioExistente.setRol(usuarioActualizado.getRol());
            }

            if (usuarioActualizado.getTienda() != null && usuarioActualizado.getTienda().getId() != null) {
                usuarioExistente.setTiendaId(usuarioActualizado.getTienda().getId());
            } else if (usuarioActualizado.getTiendaId() != null) {
                usuarioExistente.setTiendaId(usuarioActualizado.getTiendaId());
            }

            return guardarUsuario(usuarioExistente);
        });
    }
}
