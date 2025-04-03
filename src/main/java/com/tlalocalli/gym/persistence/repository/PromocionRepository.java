package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.PromocionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PromocionRepository extends JpaRepository<PromocionEntity, Integer>, JpaSpecificationExecutor<PromocionEntity> {
}
