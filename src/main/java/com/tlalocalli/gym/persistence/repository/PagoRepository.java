package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.PagoEntity;
import com.tlalocalli.gym.persistence.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Integer> {

}

