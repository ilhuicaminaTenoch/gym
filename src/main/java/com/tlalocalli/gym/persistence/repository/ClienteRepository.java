package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer>,JpaSpecificationExecutor<ClienteEntity> {
    boolean existsByEmail(String email);

}
