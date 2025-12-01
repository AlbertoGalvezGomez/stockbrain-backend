package com.stockbrain.controlador;

import com.stockbrain.modelo.entidad.EntidadAlerta;
import com.stockbrain.servicio.ServicioAlerta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
@CrossOrigin(origins = "*")
public class ControladorAlerta {

    @Autowired
    private ServicioAlerta servicioAlerta;

    @GetMapping("/tienda/{tiendaId}")
    public List<EntidadAlerta> obtenerAlertasDeTienda(@PathVariable Long tiendaId) {
        return servicioAlerta.obtenerDeTienda(tiendaId);
    }

    @GetMapping("/tienda/{tiendaId}/ultimas/{cantidad}")
    public List<EntidadAlerta> obtenerUltimasAlertas(
            @PathVariable Long tiendaId,
            @PathVariable int cantidad) {
        return servicioAlerta.obtenerUltimasDeTienda(tiendaId, cantidad);
    }
}