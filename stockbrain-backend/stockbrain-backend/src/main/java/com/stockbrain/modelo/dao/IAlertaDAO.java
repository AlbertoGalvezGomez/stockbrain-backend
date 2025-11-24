package com.stockbrain.modelo.dao;

import com.stockbrain.modelo.entidad.EntidadAlerta;
import com.stockbrain.modelo.entidad.EntidadVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IAlertaDAO extends JpaRepository<EntidadAlerta, Long> {
}
