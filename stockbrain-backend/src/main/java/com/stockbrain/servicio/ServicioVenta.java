package com.stockbrain.servicio;

import com.stockbrain.modelo.dao.IVentaDAO;
import com.stockbrain.modelo.entidad.EntidadVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioVenta {

    @Autowired
    private IVentaDAO ventaDAO;

    // Listar todas las ventas
    public List<EntidadVenta> listarVentas() {
        return ventaDAO.findAll();
    }

    // Buscar venta por ID
    public Optional<EntidadVenta> buscarVentaPorId(Long id) {
        return ventaDAO.findById(id);
    }

    // Guardar o actualizar un venta
    public EntidadVenta guardarVenta(EntidadVenta venta){
        return ventaDAO.save(venta);
    }

    // Eliminar venta por ID
    public void eliminarVentaPorId(Long id) {
        ventaDAO.deleteById(id);
    }
}
