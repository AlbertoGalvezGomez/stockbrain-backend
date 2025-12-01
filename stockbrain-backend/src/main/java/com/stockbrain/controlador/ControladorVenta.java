package com.stockbrain.controlador;

import com.stockbrain.modelo.entidad.EntidadVenta;
import com.stockbrain.servicio.ServicioVenta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@CrossOrigin(origins = {"http://192.168.1.133:8080", "http://localhost:3000"})
public class ControladorVenta {

    @Autowired
    private ServicioVenta servicioVenta;

    @GetMapping
    public ResponseEntity<List<EntidadVenta>> listarVentas() {
        return ResponseEntity.ok(servicioVenta.listarVentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadVenta> obtenerVentaPorId(@PathVariable Long id) {
        return servicioVenta.buscarVentaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntidadVenta> crearVenta(@Valid @RequestBody EntidadVenta venta) {
        EntidadVenta creada = servicioVenta.guardarVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntidadVenta> actualizarVenta(
            @PathVariable Long id,
            @Valid @RequestBody EntidadVenta ventaActualizada) {

        return servicioVenta.buscarVentaPorId(id)
                .map(ventaExistente -> {
                    ventaExistente.setFecha(ventaActualizada.getFecha());
                    ventaExistente.setCantidad(ventaActualizada.getCantidad());
                    EntidadVenta actualizada = servicioVenta.guardarVenta(ventaExistente);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        if (servicioVenta.buscarVentaPorId(id).isPresent()) {
            servicioVenta.eliminarVentaPorId(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}