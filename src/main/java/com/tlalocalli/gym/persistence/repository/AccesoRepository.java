package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.AccesoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccesoRepository extends JpaRepository<AccesoEntity, Integer> {
}
