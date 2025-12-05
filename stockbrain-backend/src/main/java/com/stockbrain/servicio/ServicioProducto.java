package com.stockbrain.servicio;

import com.stockbrain.modelo.dao.IProductoDAO;
import com.stockbrain.modelo.entidad.EntidadProducto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioProducto {

    @Autowired
    private IProductoDAO productoDAO;

    @Autowired
    private ServicioAlerta servicioAlerta;

    public List<EntidadProducto> listarTodosLosProductos() {
        return productoDAO.findAll();
    }

    public Optional<EntidadProducto> buscarProductoPorId(Long id) {
        return productoDAO.findById(id);
    }

    public EntidadProducto guardarProducto(EntidadProducto producto) {
        boolean esNuevo = producto.getId() == null;
        EntidadProducto guardado = productoDAO.save(producto);

        if (esNuevo) {
            servicioAlerta.crear("NUEVO_PRODUCTO",
                    "Producto nuevo: " + producto.getNombre() + " agregado",
                    guardado, producto.getTienda());
        } else {
            servicioAlerta.crear("PRODUCTO_EDITADO",
                    "Producto editado: " + producto.getNombre(),
                    guardado, producto.getTienda());
        }
        return guardado;
    }

    @Transactional
    public void eliminarProductoPorId(Long id) {
        EntidadProducto producto = productoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        servicioAlerta.crear(
                "PRODUCTO_ELIMINADO",
                "Producto eliminado: " + producto.getNombre(),
                null,
                producto.getTienda()
        );

        productoDAO.deleteById(id);
    }

    public List<EntidadProducto> listarPorTienda(Long tiendaId) {
        return productoDAO.findByTiendaId(tiendaId);
    }

}
