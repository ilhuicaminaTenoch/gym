package com.tlalocalli.gym.persistence.repository;

import com.tlalocalli.gym.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer>, JpaSpecificationExecutor<ProductoEntity> {
    // Para validar unicidad
    boolean existsByCodigoBarras(String codigoBarras);
}
