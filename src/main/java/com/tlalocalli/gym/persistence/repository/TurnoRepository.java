package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.TurnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface TurnoRepository extends JpaRepository<TurnoEntity, Integer> {
    Optional<TurnoEntity> findByUsuarioIdAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqual(
            Integer usuarioId,
            LocalTime horaInicio,
            LocalTime horaFin
    );
}
