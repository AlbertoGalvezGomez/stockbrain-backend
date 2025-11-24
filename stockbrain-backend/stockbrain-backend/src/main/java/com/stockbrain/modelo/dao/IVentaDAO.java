package com.stockbrain.modelo.dao;

import com.stockbrain.modelo.entidad.EntidadVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IVentaDAO extends JpaRepository<EntidadVenta, Long> {
}
