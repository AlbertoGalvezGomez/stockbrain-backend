package com.stockbrain.modelo.dao;

import com.stockbrain.modelo.entidad.EntidadProducto;
import com.stockbrain.modelo.entidad.EntidadUsuario;
import com.stockbrain.modelo.entidad.EntidadVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioDAO extends JpaRepository<EntidadUsuario, Long> {

    Optional<EntidadUsuario> findByEmail(String email);
}
