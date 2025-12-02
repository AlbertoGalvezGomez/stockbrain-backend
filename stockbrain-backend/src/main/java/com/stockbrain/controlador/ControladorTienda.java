package com.stockbrain.controlador;

import com.stockbrain.modelo.dao.ITiendaDAO;
import com.stockbrain.modelo.dao.IUsuarioDAO;
import com.stockbrain.modelo.entidad.EntidadTienda;
import com.stockbrain.modelo.entidad.EntidadUsuario;
import com.stockbrain.modelo.entidad.Rol;
import com.stockbrain.modelo.entidad.TiendaRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiendas")
@CrossOrigin(origins = "http://192.168.1.20/")
public class ControladorTienda {

    @Autowired
    private ITiendaDAO tiendaDAO;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @GetMapping
    public List<EntidadTienda> listarTiendas() {
        return tiendaDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadTienda> obtenerTienda(@PathVariable Long id) {
        return tiendaDAO.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearTiendaConAdministrador(@RequestBody TiendaRequest request) {

        if (request.getNombre() == null || request.getNombre().trim().isEmpty() ||
                request.getUbicacion() == null || request.getUbicacion().trim().isEmpty() ||
                request.getAdministradorId() == null) {
            return ResponseEntity.badRequest().body("Faltan datos obligatorios");
        }

        Long adminId = request.getAdministradorId();

        EntidadUsuario admin = usuarioDAO.findById(adminId)
                .orElse(null);

        if (admin == null) return ResponseEntity.status(404).body("Usuario no encontrado");
        if (admin.getRol() != Rol.ADMIN) return ResponseEntity.status(403).body("Solo ADMIN");
        if (admin.getTienda() != null) return ResponseEntity.badRequest().body("Ya tiene tienda");

        EntidadTienda nuevaTienda = new EntidadTienda();
        nuevaTienda.setNombre(request.getNombre().trim());
        nuevaTienda.setUbicacion(request.getUbicacion().trim());
        nuevaTienda.setAdministrador(admin);

        admin.setTienda(nuevaTienda);

        EntidadTienda guardada = tiendaDAO.save(nuevaTienda);

        return ResponseEntity.ok(guardada);
    }
}