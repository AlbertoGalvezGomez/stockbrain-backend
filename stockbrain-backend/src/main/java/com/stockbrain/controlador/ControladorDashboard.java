package com.stockbrain.controlador;

import com.stockbrain.modelo.dto.DashboardResponse;
import com.stockbrain.servicio.ServicioDashboard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ControladorDashboard {

    private final ServicioDashboard servicioDashboard;

    @GetMapping("/tienda/{tiendaId}")
    public ResponseEntity<DashboardResponse> obtenerDashboard(@PathVariable Long tiendaId) {
        DashboardResponse response = servicioDashboard.obtenerDashboard(tiendaId);
        return ResponseEntity.ok(response);
    }
}