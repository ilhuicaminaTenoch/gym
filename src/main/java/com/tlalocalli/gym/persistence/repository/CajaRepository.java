package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.CajaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CajaRepository extends JpaRepository<CajaEntity, Integer> {
}