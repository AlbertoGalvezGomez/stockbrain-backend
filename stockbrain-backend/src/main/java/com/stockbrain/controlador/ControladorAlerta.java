package com.stockbrain.controlador;

import com.stockbrain.modelo.entidad.EntidadAlerta;
import com.stockbrain.modelo.entidad.EntidadProducto;
import com.stockbrain.servicio.ServicioAlerta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
@CrossOrigin(origins = "http://192.168.1.133:8080/")
public class ControladorAlerta {

    @Autowired
    private ServicioAlerta servicioAlerta;

    @GetMapping
    public List<EntidadAlerta> listarAlertas() {
        return servicioAlerta.listarAlertas();
    }

    @GetMapping("/{id}")
    public EntidadAlerta obtenerAlertaPorId(@PathVariable Long id) {
        return servicioAlerta.buscarAlertaPorId(id).orElse(null);
    }

    @PostMapping
    public EntidadAlerta crearAlerta(@RequestBody @Valid EntidadAlerta alerta) {
        return servicioAlerta.guardarAlerta(alerta);
    }

    @DeleteMapping("/{id}")
    public void eliminarAlerta(@PathVariable Long id) {
        servicioAlerta.eliminarAlertaPorId(id);
    }

    // Actualizar alerta existente
    @PutMapping("/{id}")
    public ResponseEntity<EntidadAlerta> actualizarAlerta(
            @PathVariable Long id,
            @RequestBody @Valid EntidadAlerta alertaActualizada) {

        return servicioAlerta.buscarAlertaPorId(id)
                .map(p -> {
                    p.setTipo((alertaActualizada.getTipo()));
                    p.setMensaje(alertaActualizada.getMensaje());
                    return ResponseEntity.ok(servicioAlerta.guardarAlerta(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
