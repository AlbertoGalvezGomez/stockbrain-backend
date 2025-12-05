package com.stockbrain.modelo.dao;

import com.stockbrain.modelo.entidad.EntidadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioDAO extends JpaRepository<EntidadUsuario, Long> {

    Optional<EntidadUsuario> findByEmail(String email);
}
