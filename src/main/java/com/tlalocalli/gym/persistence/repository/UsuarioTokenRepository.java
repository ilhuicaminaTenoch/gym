package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.UsuarioTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioTokenRepository extends JpaRepository<UsuarioTokenEntity, Integer> {
}
