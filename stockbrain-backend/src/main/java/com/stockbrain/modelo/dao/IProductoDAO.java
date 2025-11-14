package com.stockbrain.modelo.dao;

import com.stockbrain.modelo.entidad.EntidadProducto;
import com.stockbrain.modelo.entidad.EntidadVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductoDAO extends JpaRepository<EntidadProducto, Long> {

    // JpaRepository ya incluye los métodos CRUD básicos, por lo que no es necesario extender CrudRepository

    List<EntidadProducto> findByTiendaId(Long tiendaId);
}
