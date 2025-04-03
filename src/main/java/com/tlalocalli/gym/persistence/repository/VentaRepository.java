package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Integer> {

}

