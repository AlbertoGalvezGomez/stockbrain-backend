package com.stockbrain.controlador;

import com.stockbrain.modelo.entidad.EntidadTienda;
import com.stockbrain.servicio.ServicioTienda;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiendas")
@CrossOrigin(origins = "http://192.168.1.133:8080/")
public class ControladorTienda {

    @Autowired
    private ServicioTienda servicioTienda;

    // Obtener todas las tiendas
    @GetMapping
    public List<EntidadTienda> listarTiendas() {
        return servicioTienda.listarTiendas();
    }

    // Obtener una tienda por ID
    @GetMapping("/{id}")
    public ResponseEntity<EntidadTienda> obtenerPorId(@PathVariable Long id) {
        return servicioTienda.buscarTiendaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tiendas/usuario/{userId}")
    public ResponseEntity<EntidadTienda> obtenerTiendaPorUsuario(@PathVariable Long userId) {
        EntidadTienda tienda = servicioTienda.findByAdminId(userId);

        if (tienda == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tienda);
    }

    // Crear nueva tienda
    @PostMapping
    public ResponseEntity<?> crearTienda(@RequestBody EntidadTienda tienda) {
        try {
            EntidadTienda nuevaTienda = servicioTienda.guardarTienda(tienda);
            return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la tienda: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar tienda
    @PutMapping("/{id}")
    public ResponseEntity<EntidadTienda> actualizarTienda(
            @PathVariable Long id,
            @RequestBody EntidadTienda tiendaActualizada) {
        return servicioTienda.buscarTiendaPorId(id)
                .map(p -> {
                    p.setNombre(tiendaActualizada.getNombre());
                    p.setUbicacion(tiendaActualizada.getUbicacion());
                    return ResponseEntity.ok(servicioTienda.guardarTienda(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar tienda
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTienda(@PathVariable Long id) {
        if (servicioTienda.buscarTiendaPorId(id).isPresent()) {
            servicioTienda.eliminarTiendaPorId(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}