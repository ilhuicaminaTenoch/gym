package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.ClienteEntity;
import com.tlalocalli.gym.persistence.entity.SuscripcionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SuscripcionRepository extends JpaRepository<SuscripcionEntity, Integer>, JpaSpecificationExecutor<SuscripcionEntity> {

}
