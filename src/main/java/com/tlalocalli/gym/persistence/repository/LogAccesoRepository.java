package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.LogAccesoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAccesoRepository extends JpaRepository<LogAccesoEntity, Integer> {
}
