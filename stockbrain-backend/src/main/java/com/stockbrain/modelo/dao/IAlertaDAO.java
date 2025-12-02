package com.stockbrain.modelo.dao;

import com.stockbrain.modelo.entidad.EntidadAlerta;
import com.stockbrain.modelo.entidad.EntidadVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAlertaDAO extends JpaRepository<EntidadAlerta, Long> {

    List<EntidadAlerta> findByTiendaIdOrderByFechaDesc(Long tiendaId);
}
