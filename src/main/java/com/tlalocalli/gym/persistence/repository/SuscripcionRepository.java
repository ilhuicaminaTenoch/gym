package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.ClienteEntity;
import com.tlalocalli.gym.persistence.entity.SuscripcionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SuscripcionRepository extends JpaRepository<SuscripcionEntity, Integer>, JpaSpecificationExecutor<SuscripcionEntity> {
    @Query(value = "select s.* from suscripcion s inner join plan_suscripcion ps on s.id_suscripcion = ps.id_plan where s.id_suscripcion = :id", nativeQuery = true)
    SuscripcionEntity findByPlanSuscripcionId(Integer id);
}
