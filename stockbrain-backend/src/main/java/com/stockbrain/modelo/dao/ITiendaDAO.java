package com.stockbrain.modelo.dao;

import com.stockbrain.modelo.entidad.EntidadTienda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITiendaDAO extends JpaRepository<EntidadTienda, Long> {

    EntidadTienda findByAdministrador_Id(Long adminId);
}
