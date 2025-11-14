package com.stockbrain.servicio;

import com.stockbrain.modelo.dao.IProductoDAO;
import com.stockbrain.modelo.entidad.EntidadProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioProducto {

    @Autowired
    private IProductoDAO productoDAO;

    // Listar todos los productos
    public List<EntidadProducto> listarTodosLosProductos() {
        return productoDAO.findAll();
    }

    // Buscar producto por ID
    public Optional<EntidadProducto> buscarProductoPorId(Long id) {
        return productoDAO.findById(id);
    }

    // Guardar o actualizar un producto
    public EntidadProducto guardarProducto(EntidadProducto producto){
        return productoDAO.save(producto);
    }

    // Eliminar producto por ID
    public void eliminarProductoPorId(Long id) {
        productoDAO.deleteById(id);
    }

    // Buscar productos por tienda
    public List<EntidadProducto> listarPorTienda(Long tiendaId) {
        return productoDAO.findByTiendaId(tiendaId);
    }

}
