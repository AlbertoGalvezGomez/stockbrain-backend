package com.stockbrain.servicio;

import com.stockbrain.modelo.dao.ITiendaDAO;
import com.stockbrain.modelo.dao.IUsuarioDAO;
import com.stockbrain.modelo.entidad.EntidadTienda;
import com.stockbrain.modelo.entidad.EntidadUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioTienda {

    @Autowired
    private ITiendaDAO tiendaDAO;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    public List<EntidadTienda> listarTiendas() {
        return tiendaDAO.findAll();
    }

    public Optional<EntidadTienda> buscarTiendaPorId(Long id) {
        return tiendaDAO.findById(id);
    }

    public EntidadTienda findByAdminId(Long adminId) {
        return tiendaDAO.findByAdministrador_Id(adminId);
    }

    public EntidadTienda guardarTienda(EntidadTienda tienda) {

        if (tienda.getAdministrador() == null || tienda.getAdministrador().getId() == null) {
            throw new ConstraintViolationException("El administrador es obligatorio", null);
        }

        EntidadUsuario admin = usuarioDAO.findById(tienda.getAdministrador().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario administrador no encontrado con ID: " + tienda.getAdministrador().getId()));

        tienda.setAdministrador(admin);

        return tiendaDAO.save(tienda);
    }

    public void eliminarTiendaPorId(Long id) {
        tiendaDAO.deleteById(id);
    }
}