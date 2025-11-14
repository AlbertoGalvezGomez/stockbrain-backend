package com.stockbrain.controlador;

import com.stockbrain.modelo.entidad.EntidadUsuario;
import com.stockbrain.modelo.entidad.EntidadVenta;
import com.stockbrain.servicio.ServicioVenta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@CrossOrigin(origins = "http://192.168.1.133:8080/")
public class ControladorVenta {

    @Autowired
    private ServicioVenta servicioVenta;

    @GetMapping
    public List<EntidadVenta> listarVentas() {
        return servicioVenta.listarVentas();
    }

    @GetMapping("/{id}")
    public EntidadVenta obtenerVentaPorId(@PathVariable Long id) {
        return servicioVenta.buscarVentaPorId(id).orElse(null);
    }

    @PostMapping
    public EntidadVenta crearVenta(@RequestBody @Valid EntidadVenta venta) {
        return servicioVenta.guardarVenta(venta);
    }

    @DeleteMapping("/{id}")
    public void eliminarVenta(@PathVariable Long id) {
        servicioVenta.eliminarVentaPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntidadVenta> actualizarVenta(
            @PathVariable Long id,
            @RequestBody @Valid EntidadVenta ventaActualizada) {

        return servicioVenta.buscarVentaPorId(id)
                .map(p -> {
                    p.setFecha((ventaActualizada.getFecha()));
                    p.setCantidad(ventaActualizada.getCantidad());
                    return ResponseEntity.ok(servicioVenta.guardarVenta(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
