package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.UsuarioDetalleEntity;
import com.tlalocalli.gym.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDetalleRepository extends JpaRepository<UsuarioDetalleEntity, Integer> {
    Optional<UsuarioDetalleEntity> findByUsuario(UsuarioEntity usuario);
}
