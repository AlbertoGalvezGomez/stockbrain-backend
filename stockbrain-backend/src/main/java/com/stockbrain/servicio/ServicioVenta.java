package com.stockbrain.servicio;

import com.stockbrain.modelo.dao.IProductoDAO;
import com.stockbrain.modelo.dao.IVentaDAO;
import com.stockbrain.modelo.entidad.EntidadProducto;
import com.stockbrain.modelo.entidad.EntidadVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioVenta {

    @Autowired
    private IVentaDAO ventaDAO;

    @Autowired
    private IProductoDAO productoDAO;

    public List<EntidadVenta> listarVentas() {
        return ventaDAO.findAll();
    }

    public Optional<EntidadVenta> buscarVentaPorId(Long id) {
        return ventaDAO.findById(id);
    }

    @Transactional  // ¡Importantísimo! Para que funcione todo en una transacción
    public EntidadVenta guardarVenta(EntidadVenta venta) {
        Long productoId = venta.getProducto().getId();
        EntidadProducto producto = productoDAO.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productoId));

        if (producto.getStock() < venta.getCantidad()) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + producto.getStock() +
                    ", solicitado: " + venta.getCantidad());
        }

        producto.setStock(producto.getStock() - venta.getCantidad());
        productoDAO.save(producto);

        venta.setTienda(producto.getTienda());

        if (venta.getFecha() == null) {
            venta.setFecha(LocalDate.now());
        }

        return ventaDAO.save(venta);
    }

    public void eliminarVentaPorId(Long id) {
        ventaDAO.deleteById(id);
    }
}