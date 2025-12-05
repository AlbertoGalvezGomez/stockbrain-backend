package com.stockbrain.modelo.dao;

import com.stockbrain.modelo.entidad.EntidadAlerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAlertaDAO extends JpaRepository<EntidadAlerta, Long> {

    List<EntidadAlerta> findByTiendaIdOrderByFechaDesc(Long tiendaId);
}
