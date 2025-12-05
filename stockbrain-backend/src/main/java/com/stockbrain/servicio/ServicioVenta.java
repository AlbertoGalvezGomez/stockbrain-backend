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

    @Autowired
    private ServicioAlerta servicioAlerta;

    public List<EntidadVenta> listarVentas() {
        return ventaDAO.findAll();
    }

    public Optional<EntidadVenta> buscarVentaPorId(Long id) {
        return ventaDAO.findById(id);
    }

    @Transactional
    public EntidadVenta guardarVenta(EntidadVenta venta) {
        Long productoId = venta.getProducto().getId();
        EntidadProducto producto = productoDAO.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productoId));

        if (producto.getStock() < venta.getCantidad()) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + producto.getStock() +
                    ", solicitado: " + venta.getCantidad());
        }

        // ACTUALIZAR EL STOCK DEL PRODUCTO
        producto.setStock(producto.getStock() - venta.getCantidad());
        productoDAO.save(producto);

        // COMPLETAR LOS DATOS DE LA VENTA
        venta.setTienda(producto.getTienda());
        if (venta.getFecha() == null) {
            venta.setFecha(LocalDate.now());
        }

        // GUARDAR LA VENTA
        EntidadVenta ventaGuardada = ventaDAO.save(venta);

        // CREAR EL MENSAJE EN ALERTAS (DESPUÉS del save)
        servicioAlerta.crear(
                "VENTA_REALIZADA",
                "Venta realizada: " + venta.getCantidad() + " unidades de " + producto.getNombre(),
                producto,
                producto.getTienda()
        );

        // RETORNAR LA VENTA GUARDADA
        return ventaGuardada;
    }

    public void eliminarVentaPorId(Long id) {
        ventaDAO.deleteById(id);
    }
}