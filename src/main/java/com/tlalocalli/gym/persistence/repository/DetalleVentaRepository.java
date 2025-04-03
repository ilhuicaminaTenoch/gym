package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.DetalleVentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVentaEntity, Integer> {

}

