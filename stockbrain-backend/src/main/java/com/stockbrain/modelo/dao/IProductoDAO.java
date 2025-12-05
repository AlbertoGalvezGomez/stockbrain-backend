package com.stockbrain.modelo.dao;

import com.stockbrain.modelo.entidad.EntidadProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductoDAO extends JpaRepository<EntidadProducto, Long> {

    List<EntidadProducto> findByTiendaId(Long tiendaId);
}
