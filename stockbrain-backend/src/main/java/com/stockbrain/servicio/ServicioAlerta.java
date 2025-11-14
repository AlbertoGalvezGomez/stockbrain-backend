package com.stockbrain.servicio;

import com.stockbrain.modelo.dao.IAlertaDAO;
import com.stockbrain.modelo.entidad.EntidadAlerta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioAlerta {

    @Autowired
    private IAlertaDAO alertaDAO;

    // Listar todas las alertas
    public List<EntidadAlerta> listarAlertas() {
        return alertaDAO.findAll();
    }

    // Buscar alerta por ID
    public Optional<EntidadAlerta> buscarAlertaPorId(Long id) {
        return alertaDAO.findById(id);
    }

    // Guardar o actualizar una alerta
    public EntidadAlerta guardarAlerta(EntidadAlerta alerta){
        return alertaDAO.save(alerta);
    }

    // Eliminar alerta por ID
    public void eliminarAlertaPorId(Long id) {
        alertaDAO.deleteById(id);
    }
}
