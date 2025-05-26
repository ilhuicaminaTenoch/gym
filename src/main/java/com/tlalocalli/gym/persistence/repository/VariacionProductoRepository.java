package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.VariacionProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariacionProductoRepository extends JpaRepository<VariacionProductoEntity, Integer> {
    List<VariacionProductoEntity> findByProductoId(Integer productoId);
    Optional<VariacionProductoEntity> findByProductoIdAndSku(Integer productoId, String sku);
}
