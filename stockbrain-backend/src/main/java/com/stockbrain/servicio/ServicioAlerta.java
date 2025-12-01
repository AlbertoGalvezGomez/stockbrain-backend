package com.stockbrain.servicio;

import com.stockbrain.modelo.dao.IAlertaDAO;
import com.stockbrain.modelo.entidad.EntidadAlerta;
import com.stockbrain.modelo.entidad.EntidadProducto;
import com.stockbrain.modelo.entidad.EntidadTienda;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioAlerta {

    private final IAlertaDAO alertaDAO;

    public void crear(String tipo, String mensaje, EntidadProducto producto, EntidadTienda tienda) {
        EntidadAlerta alerta = new EntidadAlerta();
        alerta.setTipo(tipo);
        alerta.setMensaje(mensaje);
        alerta.setProducto(producto);
        alerta.setTienda(tienda);
        alerta.setFecha(LocalDateTime.now());
        alertaDAO.save(alerta);
    }

    public List<EntidadAlerta> obtenerUltimasDeTienda(Long tiendaId, int cantidad) {
        return alertaDAO.findByTiendaIdOrderByFechaDesc(tiendaId)
                .stream()
                .limit(cantidad)
                .collect(Collectors.toList());
    }

    public List<EntidadAlerta> obtenerDeTienda(Long tiendaId) {
        return alertaDAO.findByTiendaIdOrderByFechaDesc(tiendaId);
    }
}
